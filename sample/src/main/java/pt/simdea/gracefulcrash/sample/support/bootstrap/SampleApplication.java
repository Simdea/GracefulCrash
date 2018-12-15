package pt.simdea.gracefulcrash.sample.support.bootstrap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import lombok.Getter;
import pt.simdea.gracefulcrash.bootstrap.DefaultErrorActivity;
import pt.simdea.gracefulcrash.bootstrap.GracefulCrashConfiguration;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashBackgroundModes;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;
import pt.simdea.gracefulcrash.sample.BuildConfig;
import pt.simdea.gracefulcrash.sample.support.data.constants.ActivityStateConstants;
import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;
import pt.simdea.gracefulcrash.sample.support.data.constants.MemoryConstants;
import pt.simdea.gracefulcrash.sample.support.data.constants.SampleConstants;
import pt.simdea.gracefulcrash.sample.support.injection.component.ApplicationComponent;

/**
 * Sample Global Application class.
 */
public class SampleApplication extends Application
        implements Application.ActivityLifecycleCallbacks {

    @Getter
    protected ApplicationComponent mApplicationComponent;

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this); /* register activity lifecycle callbacks */
        initializeLibraries(); /* initialize any library configuration / call */
        initializeDevTools(); /* enable development logs and other tools */
        mApplicationComponent.inject(this); /* inject the application component via Dagger library */
    }

    /** {@inheritDoc} */
    @Override
    public void onTerminate() {
        Log.d(SampleConstants.LOG_TAG, SampleConstants.APPLICATION_TERMINATED);
        super.onTerminate();
    }

    /**
     * This procedure is called when the overall system is running low on memory,
     * and would like actively running processes to tighten their belts.
     * The system will only call this procedure in Android versions below 14.
     */
    @Override
    public void onLowMemory() {
        Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_LOW_MEMORY);
        super.onLowMemory();
    }

    /**
     * This procedure is called when the operating system has determined that it is a good time for a process
     * to trim unneeded memory from its process.
     * The system will only call this procedure in Android versions above or equal to 14.
     */
    @Override
    public void onTrimMemory(final int level) {
        Log.i(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_LOW_MEMORY);
        switch (level) {
            case TRIM_MEMORY_RUNNING_MODERATE:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_RUNNING_MODERATE);
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_RUNNING_LOW);
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_RUNNING_CRITICAL);
                break;
            case TRIM_MEMORY_BACKGROUND:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_BACKGROUND);
                break;
            case TRIM_MEMORY_MODERATE:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_MODERATE);
                break;
            case TRIM_MEMORY_COMPLETE:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_COMPLETE);
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                Log.w(SampleConstants.LOG_TAG, MemoryConstants.SYSTEM_TRIM_MEMORY_UI_HIDDEN);
                break;
            default:
                throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_OPERATION_ERROR);
        }
        super.onTrimMemory(level);
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityCreated(@NonNull final Activity activity, @Nullable final Bundle savedInstanceState) {
        Log.w(SampleConstants.LOG_TAG, String
                .format(ActivityStateConstants.ACTIVITY_CALLBACK_CREATED, activity.getClass().getSimpleName()));
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityStarted(@NonNull final Activity activity) {
        Log.w(SampleConstants.LOG_TAG, String.
                format(ActivityStateConstants.ACTIVITY_CALLBACK_STARTED, activity.getClass().getSimpleName()));
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityResumed(@NonNull final Activity activity) {
        Log.w(SampleConstants.LOG_TAG, String
                .format(ActivityStateConstants.ACTIVITY_CALLBACK_RESUMED, activity.getClass().getSimpleName()));
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityPaused(@NonNull final Activity activity) {
        Log.w(SampleConstants.LOG_TAG, String
                .format(ActivityStateConstants.ACTIVITY_CALLBACK_PAUSED, activity.getClass().getSimpleName()));
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityStopped(@NonNull final Activity activity) {
        Log.w(SampleConstants.LOG_TAG, String
                .format(ActivityStateConstants.ACTIVITY_CALLBACK_STOPPED, activity.getClass().getSimpleName()));
    }

    /** {@inheritDoc} */
    @Override
    public void onActivitySaveInstanceState(@NonNull final Activity activity,
                                            @NonNull final Bundle outState) {
        Log.w(SampleConstants.LOG_TAG, String.format(ActivityStateConstants.ACTIVITY_CALLBACK_SAVE_INSTANCE_STATE,
                activity.getClass().getSimpleName()));
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityDestroyed(@NonNull final Activity activity) {
        Log.w(SampleConstants.LOG_TAG, String.format(ActivityStateConstants.ACTIVITY_CALLBACK_DESTROYED,
                activity.getClass().getSimpleName()));
    }

    /**
     * Procedure meant to provide the global application context instance.
     * @param context {@link Context} instance representing the application's current context.
     * @return        {@link SampleApplication} instance representing the global application instance.
     */
    @SuppressWarnings("unused")
    public static SampleApplication get(@NonNull final Context context) {
        return (SampleApplication) context.getApplicationContext();
    }

    /**
     * Procedure meant to initialize libraries used in this application.
     * Add any library configuration / call here.
     */
    private void initializeLibraries() {
        mApplicationComponent = LibraryInitializer.initializeDagger(this); /* Dagger2 library */
        // Add more library initialization calls here via LibraryInitializer...
    }

    /**
     * Procedure meant to enable development logs and other tools.
     */
    private void initializeDevTools() {
        if (BuildConfig.DEBUG) {
            StrictModeAdmin.enableStrictModeThreadPolicy();
            StrictModeAdmin.enableStrictModeVMPolicy();
        }
        setExceptionHandlerOptions();
    }

    private void setExceptionHandlerOptions() {
        final GracefulCrashConfiguration configuration = new GracefulCrashConfiguration.ConfigurationBuilder()
                .enable(true)
                .trackActivities(true, GracefulCrashConstants.DEFAULT_DATE_FORMAT, 2)
                .withBackgroundMode(GracefulCrashBackgroundModes.SHOW_CUSTOM_CRASH)
                .logErrorOnRestart(true)
                .withErrorActivity(DefaultErrorActivity.class)
                .buildConfiguration();
        configuration.apply();
    }

}
