package pt.simdea.gracefulcrash.handlers;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import pt.simdea.gracefulcrash.bootstrap.GracefulCrash;
import pt.simdea.gracefulcrash.bootstrap.GracefulCrashActivityTracker;
import pt.simdea.gracefulcrash.bootstrap.GracefulCrashConfiguration;
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

        final GracefulCrashConfiguration configuration = GracefulCrash.getInstance().getConfiguration();
        final GracefulCrashActivityTracker activityTracker = GracefulCrashActivityTracker.getInstance();
        final Class<? extends Activity> errorActivityClass = configuration.getErrorActivity();

        if (configuration.getBackgroundMode() == GracefulCrashBackgroundModes.SHOW_CUSTOM_CRASH
                && !GracefulCrashActivityTracker.getInstance().mInBackground) {

            final Intent errorIntent = new Intent(mApplication, errorActivityClass);
            errorIntent.putExtra(GracefulCrashConstants.ACTIVITY_LOG_DATA,
                    compileActivityTracking(activityTracker, configuration));
            errorIntent.putExtra(GracefulCrashConstants.ACTIVITY_ERROR_LOG_ON_RESTART_DATA,
                    compileStackTrace(throwable));
            errorIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activityTracker.mLastActivityCreated.get().startActivity(errorIntent);
            activityTracker.mLastActivityCreated.get().finish();
            activityTracker.mLastActivityCreated.clear();

        } else if (configuration.getBackgroundMode() == GracefulCrashBackgroundModes.SHOW_SYSTEM_CRASH
                && !GracefulCrashActivityTracker.getInstance().mInBackground) {
            mAnalyticsExceptionHandler.uncaughtException(thread, throwable);
            mSystemExceptionHandler.uncaughtException(thread, throwable);
        }
        exit();
    }

    private String compileStackTrace(@NonNull final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return checkIfStackTraceIsTooLarge(stringWriter.toString());
    }

    private String checkIfStackTraceIsTooLarge(@NonNull final String stackTrace) {
        final int maxStackTraceSize = 131071; // 128 KB - 1
        if (stackTrace.length() > maxStackTraceSize) {
            return stackTrace.substring(0, maxStackTraceSize
                    - GracefulCrashConstants.DISCLAIMER_FOR_STACK_TRACE_TOO_LARGE.length())
                    + GracefulCrashConstants.DISCLAIMER_FOR_STACK_TRACE_TOO_LARGE;
        }
        return stackTrace;
    }

    private String compileActivityTracking(@NonNull final GracefulCrashActivityTracker activityTracker,
                                           @NonNull final GracefulCrashConfiguration configuration) {
        final StringBuilder resBuilder = new StringBuilder();
        if (configuration.isTrackActivities()) {
            while (!activityTracker.mActivityRecord.isEmpty()) {
                resBuilder.append(activityTracker.mActivityRecord.poll())
                        .append(GracefulCrashConstants.SINGLE_NEW_LINE_STRING);
            }
        }
        return resBuilder.toString();
    }

}
