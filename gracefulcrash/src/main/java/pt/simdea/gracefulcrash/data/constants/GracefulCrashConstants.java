package pt.simdea.gracefulcrash.data.constants;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Utility class meant to hold all Graceful Crash {@link String} Constants.
 */
@Retention(RetentionPolicy.CLASS)
@StringDef({
        GracefulCrashConstants.TAG,
        GracefulCrashConstants.EMPTY_STRING,
        GracefulCrashConstants.SINGLE_TWO_POINTS_STRING,
        GracefulCrashConstants.SINGLE_NEW_LINE_STRING,
        GracefulCrashConstants.DEFAULT_DATE_FORMAT,
        GracefulCrashConstants.PROVIDER_ID,
        GracefulCrashConstants.GRACEFUL_CRASH_PACKAGE_NAME,
        GracefulCrashConstants.SYSTEM_HANDLER_PACKAGE_NAME,
        GracefulCrashConstants.INSTALL_FAILED,
        GracefulCrashConstants.INVALID_ARGUMENT,
        GracefulCrashConstants.INVALID_PROVIDER_AUTHORITY,
        GracefulCrashConstants.SINGLETON_INSTANTIATION_ERROR,
        GracefulCrashConstants.INSTANTIATING_UTILITY_CLASS_NOT_ALLOWED,
        GracefulCrashConstants.INSTANTIATING_EXCEPTION_HANDLER_CLASS_FAILED,
        GracefulCrashConstants.INSTALLING_NOW,
        GracefulCrashConstants.ALREADY_INSTALLED,
        GracefulCrashConstants.INSTALL_FINISHED,
        GracefulCrashConstants.INSTALL_OVERRIDE,
        GracefulCrashConstants.DEFAULT_SYSTEM_HANDLER_INSTALLED,
        GracefulCrashConstants.DISCLAIMER_FOR_STACK_TRACE_TOO_LARGE,
        GracefulCrashConstants.ACTIVITY_CREATED,
        GracefulCrashConstants.ACTIVITY_RESUMED,
        GracefulCrashConstants.ACTIVITY_PAUSED,
        GracefulCrashConstants.ACTIVITY_DESTROYED,
        GracefulCrashConstants.EXCEPTION_HANDLER_SYSTEM,
        GracefulCrashConstants.EXCEPTION_HANDLER_RESTARTED,
        GracefulCrashConstants.EXCEPTION_HANDLER_LAST_EXCEPTION,
        GracefulCrashConstants.ACTIVITY_LOG_DATA,
        GracefulCrashConstants.ACTIVITY_ERROR_LOG_ON_RESTART_DATA,
        GracefulCrashConstants.ACTIVITY_UNABLE_TO_PROCESS_INTENT_DATA
})
public @interface GracefulCrashConstants {

    /* Misc. Constants. */
    String TAG = ".:GracefulCrash:.";
    String EMPTY_STRING = "";
    String SINGLE_TWO_POINTS_STRING = ": ";
    String SINGLE_NEW_LINE_STRING = "\n";
    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /* Authority Constants. */
    String PROVIDER_ID = "pt.simdea.gracefulcrash.gracefulcrashinitprovider";

    /* Package Constants. */
    String GRACEFUL_CRASH_PACKAGE_NAME = "pt.simdea.gracefulcrash";
    String SYSTEM_HANDLER_PACKAGE_NAME = "com.android.internal.os";

    /* Exception Constants. */
    String INSTALL_FAILED = "Install failed: context is null!";
    String INVALID_ARGUMENT = "ProviderInfo cannot be null.";
    String INVALID_PROVIDER_AUTHORITY = "Incorrect provider authority in manifest." +
            " Most likely due to a missing applicationId variable in application\\'s build.gradle.";
    String SINGLETON_INSTANTIATION_ERROR
            = "Instantiating singleton class. Use getInstance() method to get the single instance of this class.";
    String INSTANTIATING_UTILITY_CLASS_NOT_ALLOWED = "Instantiating utility class.";
    String INSTANTIATING_EXCEPTION_HANDLER_CLASS_FAILED = "Instantiating Exception Handler class.\nUsing " +
            "GracefulCrash default handler instead.";

    /* Info Constants. */
    String INSTALLING_NOW = "Installing now ...";
    String ALREADY_INSTALLED = "Install finished.\nGracefulCrash was already installed.";
    String INSTALL_FINISHED = "Install finished.\nGracefulCrash install completed successfully.";
    String INSTALL_OVERRIDE = "IMPORTANT WARNING!\nAnother Custom UncaughtExceptionHandler detected!\nIf the " +
            "detected custom UncaughtExceptionHandler is to be used, you must initialize it AFTER GracefulCrash! " +
            "Installing GracefulCrash anyway, but the original detected handler will not be called.";
    String DEFAULT_SYSTEM_HANDLER_INSTALLED = "The default UncaughtExceptionHandler was detected.\nInstalling" +
            " GracefulCrash.";
    String DISCLAIMER_FOR_STACK_TRACE_TOO_LARGE = " [stack trace too large]";

    /* Activity Tracking Constants. */
    String ACTIVITY_CREATED = " created.";
    String ACTIVITY_RESUMED = " resumed.";
    String ACTIVITY_PAUSED = " paused.";
    String ACTIVITY_DESTROYED = " destroyed.";

    /* Restart Once Exception Handler Messages. */
    String EXCEPTION_HANDLER_SYSTEM = "The system exception handler will handle the caught exception.";
    String EXCEPTION_HANDLER_RESTARTED = "appExceptionHandler_restarted";
    String EXCEPTION_HANDLER_LAST_EXCEPTION  = "appExceptionHandler_lastException";

    /* Error Activity Arguments. */
    String ACTIVITY_LOG_DATA = GRACEFUL_CRASH_PACKAGE_NAME + ".ACTIVITY_LOG_DATA";
    String ACTIVITY_ERROR_LOG_ON_RESTART_DATA = GRACEFUL_CRASH_PACKAGE_NAME + ".ERROR_LOG_ON_RESTART_DATA";
    String ACTIVITY_UNABLE_TO_PROCESS_INTENT_DATA = "Error while processing intent data.";

}
