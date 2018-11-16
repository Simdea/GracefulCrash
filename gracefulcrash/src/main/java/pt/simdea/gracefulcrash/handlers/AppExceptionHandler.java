package pt.simdea.gracefulcrash.handlers;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import pt.simdea.gracefulcrash.bootstrap.GracefulCrash;
import pt.simdea.gracefulcrash.bootstrap.GracefulCrashActivityTracker;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * Base class responsible for handling any crashes the application experiences.
 */
@SuppressWarnings("unused")
abstract class AppExceptionHandler
        implements Thread.UncaughtExceptionHandler, Application.ActivityLifecycleCallbacks {

    final Thread.UncaughtExceptionHandler mSystemExceptionHandler;
    final Thread.UncaughtExceptionHandler mAnalyticsExceptionHandler;

    /**
     * Instantiates a new AppExceptionHandler.
     */
    AppExceptionHandler(@NonNull final Thread.UncaughtExceptionHandler systemHandler,
                        @NonNull final Thread.UncaughtExceptionHandler analyticsHandler,
                        @NonNull final Application application) {
        mSystemExceptionHandler = systemHandler;
        mAnalyticsExceptionHandler = analyticsHandler;
        application.registerActivityLifecycleCallbacks(this);
    }

    /** {@inheritDoc} */
    @Override
    public void uncaughtException(@Nullable final Thread thread, @NonNull final Throwable throwable) {
        if (GracefulCrash.getInstance().getConfiguration().isEnabled()) {
            handleUncaughtException(thread, throwable);
        } else {
            mSystemExceptionHandler.uncaughtException(thread, throwable);
            mAnalyticsExceptionHandler.uncaughtException(thread, throwable);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityCreated(@NonNull final Activity activity, @Nullable final Bundle bundle) {
        GracefulCrashActivityTracker.getInstance().mLastActivityCreated = new WeakReference<>(activity);
        if (GracefulCrash.getInstance().getConfiguration().isTrackActivities()) {
            GracefulCrashActivityTracker.getInstance()
                    .addLifecycleEvent(activity.getClass().getSimpleName(), GracefulCrashConstants.ACTIVITY_CREATED);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityStarted(@NonNull final Activity activity) {
        final GracefulCrashActivityTracker activityTracker = GracefulCrashActivityTracker.getInstance();
        activityTracker.mCurrentlyStartedActivities++;
        activityTracker.mInBackground = (activityTracker.mCurrentlyStartedActivities == 0);
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityResumed(@NonNull final Activity activity) {
        if (GracefulCrash.getInstance().getConfiguration().isTrackActivities()) {
            GracefulCrashActivityTracker.getInstance()
                    .addLifecycleEvent(activity.getClass().getSimpleName(), GracefulCrashConstants.ACTIVITY_RESUMED);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityPaused(@NonNull final Activity activity) {
        if (GracefulCrash.getInstance().getConfiguration().isTrackActivities()) {
            GracefulCrashActivityTracker.getInstance()
                    .addLifecycleEvent(activity.getClass().getSimpleName(), GracefulCrashConstants.ACTIVITY_PAUSED);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityStopped(@NonNull final Activity activity) {
        final GracefulCrashActivityTracker activityTracker = GracefulCrashActivityTracker.getInstance();
        activityTracker.mCurrentlyStartedActivities--;
        activityTracker.mInBackground = (activityTracker.mCurrentlyStartedActivities == 0);
    }

    /** {@inheritDoc} */
    @Override
    public void onActivitySaveInstanceState(@NonNull final Activity activity, @Nullable final Bundle bundle) {
        /* Do nothing here ... */
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityDestroyed(@NonNull final Activity activity) {
        if (GracefulCrash.getInstance().getConfiguration().isTrackActivities()) {
            GracefulCrashActivityTracker.getInstance()
                    .addLifecycleEvent(activity.getClass().getSimpleName(), GracefulCrashConstants.ACTIVITY_DESTROYED);
        }
    }

    /**
     * Procedure meant to terminate a running application.
     * The status code used is an arbitrary positive number status code. This is because there is only one general rule,
     * which is that exit status 0 assumes that your program completed without error. Furthermore a negative exit status
     * is usually assumed to be a termination because of an unexpected error, while a positive exit status is assumed to
     * be a (more or less) graceful termination.
     */
    void exit() {
        Process.killProcess(Process.myPid());
        System.exit(10); // Arbitrary status code
    }

    /**
     * Procedure meant to handle uncaught exceptions, via our custom app exception handler implementation.
     * @param thread    {@link Thread} instance representing the thread that threw the exception.
     * @param throwable {@link Throwable} instance representing the exception thrown.
     */
    abstract void handleUncaughtException(@Nullable final Thread thread, @NonNull final Throwable throwable);

}
