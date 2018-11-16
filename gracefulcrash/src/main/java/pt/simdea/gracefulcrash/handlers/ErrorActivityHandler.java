package pt.simdea.gracefulcrash.handlers;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import pt.simdea.gracefulcrash.bootstrap.DefaultErrorActivity;
import pt.simdea.gracefulcrash.bootstrap.GracefulCrash;
import pt.simdea.gracefulcrash.bootstrap.GracefulCrashActivityTracker;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashBackgroundModes;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * TODO PR: Add proper JavaDoc!
 */
@SuppressWarnings("unused")
public final class ErrorActivityHandler extends AppExceptionHandler {

    @NonNull
    private final Application mApplication;

    /**
     * Instantiates a new AppExceptionHandler.
     */
    public ErrorActivityHandler(@NonNull final Thread.UncaughtExceptionHandler systemHandler,
                                @NonNull final Thread.UncaughtExceptionHandler analyticsHandler,
                                @NonNull final Application application) {
        super(systemHandler, analyticsHandler, application);
        mApplication = application;
    }

    /** {@inheritDoc} */
    @Override
    void handleUncaughtException(@Nullable final Thread thread, @NonNull final Throwable throwable) {
        Log.e(GracefulCrashConstants.TAG, throwable.getMessage(), throwable);
        // TODO PR: Add implementation


        final GracefulCrashActivityTracker activityTracker = GracefulCrashActivityTracker.getInstance();
        final Class<? extends Activity> errorActivityClass = DefaultErrorActivity.class; // TODO PR: Add this to config

        if (GracefulCrash.getInstance()
                .getConfiguration().getBackgroundMode() == GracefulCrashBackgroundModes.SHOW_CUSTOM_CRASH
                || !GracefulCrashActivityTracker.getInstance().mInBackground) {

            final Intent errorIntent = new Intent(mApplication, errorActivityClass);
            errorIntent.putExtra(GracefulCrashConstants.ACTIVITY_LOG_DATA, compileActivityTracking(activityTracker));
            errorIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activityTracker.mLastActivityCreated.get().startActivity(errorIntent);
            activityTracker.mLastActivityCreated.get().finish();
            activityTracker.mLastActivityCreated.clear();

        } else if (GracefulCrash.getInstance()
                .getConfiguration().getBackgroundMode() == GracefulCrashBackgroundModes.SHOW_SYSTEM_CRASH) {
            mAnalyticsExceptionHandler.uncaughtException(thread, throwable);
            mSystemExceptionHandler.uncaughtException(thread, throwable);
        }
        exit();
    }

    private String compileActivityTracking(@NonNull final GracefulCrashActivityTracker activityTracker) {
        final StringBuilder resBuilder = new StringBuilder();
        if (GracefulCrash.getInstance().getConfiguration().isTrackActivities()) {
            while (!activityTracker.mActivityRecord.isEmpty()) {
                resBuilder.append(activityTracker.mActivityRecord.poll())
                        .append(GracefulCrashConstants.SINGLE_NEW_LINE_STRING);
            }
        }
        return resBuilder.toString();
    }

}
