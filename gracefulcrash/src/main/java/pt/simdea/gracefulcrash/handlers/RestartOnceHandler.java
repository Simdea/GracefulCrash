package pt.simdea.gracefulcrash.handlers;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import pt.simdea.gracefulcrash.bootstrap.GracefulCrashActivityTracker;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * Class meant to define an exception handler with a restart once policy.
 * The goal is the make the application recover from a crash and give the user a chance to resume an ongoing task.
 * The policy is as follows:
 * 1. If a crash happens, the app will restart on the same context and attempt to resume
 * execution.
 * 2. If after a restart, the same crash occurs, the app will crash normally.
 */
@SuppressWarnings("unused")
public final class RestartOnceHandler extends AppExceptionHandler {

    /**
     * Instantiates a new AppExceptionHandler.
     */
    public RestartOnceHandler(@NonNull final Thread.UncaughtExceptionHandler systemHandler,
                              @NonNull final Thread.UncaughtExceptionHandler analyticsHandler,
                              @NonNull final Application application) {
        super(systemHandler, analyticsHandler, application);
    }

    /** {@inheritDoc} */
    @Override
    void handleUncaughtException(@Nullable final Thread thread, @NonNull final Throwable throwable) {
        Log.e(GracefulCrashConstants.TAG, throwable.getMessage(), throwable);
        final GracefulCrashActivityTracker activityTracker = GracefulCrashActivityTracker.getInstance();
        if (activityTracker.mLastActivityCreated != null) {
            tryRecovery(activityTracker.mLastActivityCreated.get(), thread, throwable);
        } else {
            mAnalyticsExceptionHandler.uncaughtException(thread, throwable);
            mSystemExceptionHandler.uncaughtException(thread, throwable);
            exit();
        }
    }

    private void tryRecovery(@NonNull final Activity activity, @Nullable final Thread thread,
                             @NonNull final Throwable throwable) {
        final boolean isRestarted = activity.getIntent()
                .getBooleanExtra(GracefulCrashConstants.EXCEPTION_HANDLER_RESTARTED, false);
        final Throwable lastException = (Throwable) activity.getIntent()
                .getSerializableExtra(GracefulCrashConstants.EXCEPTION_HANDLER_LAST_EXCEPTION);

        if (!isRestarted || isSameException(throwable, lastException)) {
            killThisProcessAndRestart(activity, thread, throwable);
        } else {
            Log.d(GracefulCrashConstants.TAG, GracefulCrashConstants.EXCEPTION_HANDLER_SYSTEM);
            mSystemExceptionHandler.uncaughtException(thread, throwable);
            exit();
        }
    }

    private boolean isSameException(@NonNull final Throwable originalException,
                                    @Nullable final Throwable lastException) {
        if (lastException == null) {
            return false;
        }
        final StackTraceElement[] originalExceptionStack = originalException.getStackTrace();
        final StackTraceElement[] lastExceptionStack = lastException.getStackTrace();
        return originalException.getClass() == lastException.getClass()
                && originalExceptionStack[0] == lastExceptionStack[0]
                && originalException.getMessage().equals(lastException.getMessage());
    }

    private void killThisProcessAndRestart(@Nullable final Activity activity, @Nullable final Thread thread,
                                           @NonNull final Throwable throwable) {
        mAnalyticsExceptionHandler.uncaughtException(thread, throwable); // signal exception to be logged by crashlytics

        if (activity != null) {
            final GracefulCrashActivityTracker activityTracker = GracefulCrashActivityTracker.getInstance();
            final Intent restartIntent = new Intent(activity, activity.getClass());
            restartIntent.putExtra(GracefulCrashConstants.EXCEPTION_HANDLER_RESTARTED, true);
            restartIntent.putExtra(GracefulCrashConstants.EXCEPTION_HANDLER_LAST_EXCEPTION, throwable);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.finish();
            activityTracker.mLastActivityCreated.clear();
            activity.startActivity(restartIntent);
        }

        exit();
    }

}
