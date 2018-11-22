package pt.simdea.gracefulcrash.bootstrap;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;
import pt.simdea.gracefulcrash.data.exceptions.GracefulCrashModuleInstantiationException;
import pt.simdea.gracefulcrash.handlers.AppExceptionHandler;
import pt.simdea.gracefulcrash.handlers.RestartOnceHandler;

/**
 * TODO PR; Add JavaDoc...
 * https://github.com/RohitSurwase/UCE-Handler
 * https://github.com/Ereza/CustomActivityOnCrash
 * https://medium.com/@andretietz/auto-initialize-your-android-library-2349daf06920
 */
public final class GracefulCrash {

    /** {@link GracefulCrash} sole instance. */
    private static volatile GracefulCrash sGracefulCrash;

    @Getter
    @Setter
    private GracefulCrashConfiguration mConfiguration;

    /* ############################################### Constructors ############################################### */

    /**
     * Instantiates a new {@link GracefulCrash}.
     * Private to prevent instantiation as per Singleton Pattern rules.
     * @throws GracefulCrashModuleInstantiationException if the constructor is called via Reflection API.
     */
    private GracefulCrash() {
        /* Prevent from the reflection api. */
        if (sGracefulCrash != null){
            throw new GracefulCrashModuleInstantiationException(GracefulCrashConstants.SINGLETON_INSTANTIATION_ERROR);
        }
        mConfiguration = new GracefulCrashConfiguration
                .ConfigurationBuilder()
                .buildConfiguration();
    }

    /**
     * Procedure meant to retrieve this class' sole instance as per Singleton Pattern rules.
     * @return {@link GracefulCrash} instance representing this class' sole instance as per Singleton
     *         Pattern rules.
     */
    public static GracefulCrash getInstance() {
        /* Double check locking pattern */
        if (sGracefulCrash == null) { // check for the first time
            synchronized (GracefulCrash.class) { // Check for the second time.
                // if there is no instance available... create new one
                if (sGracefulCrash == null) {
                    sGracefulCrash = new GracefulCrash();
                }
            }
        }
        return sGracefulCrash;
    }

    /* ############################################### Constructors ############################################### */

    /**
     * Procedure meant to install the {@link GracefulCrash} module on the {@link android.app.Application} instance.
     */
    public void install(@Nullable final Context applicationContext, @NonNull final Class handlerClass) {
        if (applicationContext == null) {
            Log.e(GracefulCrashConstants.TAG, GracefulCrashConstants.INSTALL_FAILED);
        } else {
            Log.i(GracefulCrashConstants.TAG, GracefulCrashConstants.INSTALLING_NOW); // proceed with install
            if (!checkForPreviousInstall()) {
                proceedWithInstall((Application) applicationContext, handlerClass);
            }
        }
    }

    private void proceedWithInstall(@NonNull final Application application, @NonNull final Class handlerClass) {
        // 1. Get the current handler.
        final Thread.UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 2. Set the default handler as a dummy (so that crashlytics fallbacks to this one, once set).
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            /** {@inheritDoc} */
            @Override
            public void uncaughtException(@NonNull final Thread thread, @NonNull final Throwable throwable) {
                /* Do nothing here ... */
            }

        });

        // 3. Setup crashlytics so that it becomes the default handler (and fallback to our dummy handler).
        // Fabric.with(this, Crashlytics()) // Add crashlytics later if needed.
        final Thread.UncaughtExceptionHandler fabricExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 4. Setup our handler, which tries to restart the app.
        try {
            Thread.setDefaultUncaughtExceptionHandler(compileTargetExceptionHandler(handlerClass, currentHandler,
                    fabricExceptionHandler, application));
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Log.e(GracefulCrashConstants.TAG, GracefulCrashConstants.INSTANTIATING_EXCEPTION_HANDLER_CLASS_FAILED, e);
            Thread.setDefaultUncaughtExceptionHandler(new RestartOnceHandler(currentHandler, fabricExceptionHandler,
                    application));
        }

        Log.i(GracefulCrashConstants.TAG, GracefulCrashConstants.INSTALL_FINISHED);
    }

    private AppExceptionHandler compileTargetExceptionHandler(@NonNull final Class handlerClass,
                                              @NonNull final Thread.UncaughtExceptionHandler currentHandler,
                                              @NonNull final Thread.UncaughtExceptionHandler fabricExceptionHandler,
                                              @NonNull final Application application)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        final Constructor[] constructors = handlerClass.getConstructors();
        final boolean validConstructors = checkConstructorsArray(constructors);
        return validConstructors
                ? instantiateExceptionHandler(constructors, currentHandler, fabricExceptionHandler, application)
                : new RestartOnceHandler(currentHandler, fabricExceptionHandler, application);
    }

    private AppExceptionHandler instantiateExceptionHandler(@NonNull final Constructor[] constructors,
                                                @NonNull final Thread.UncaughtExceptionHandler currentHandler,
                                                @NonNull final Thread.UncaughtExceptionHandler fabricExceptionHandler,
                                                @NonNull final Application application)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (final Constructor constructor : constructors) {
            final List<Class> parameterTypes = Arrays.asList(constructor.getParameterTypes());
            if (checkCorrectConstructorParameters(parameterTypes)) {
                return (AppExceptionHandler) constructor
                        .newInstance(currentHandler, fabricExceptionHandler, application);
            }
        }
        return new RestartOnceHandler(currentHandler, fabricExceptionHandler, application);
    }

    private boolean checkConstructorsArray(@Nullable final Constructor[] constructors) {
        return constructors != null && constructors.length > 0;
    }

    private boolean checkCorrectConstructorParameters(@NonNull final List<Class> parameterTypes) {
        return parameterTypes.size() == 3
                && parameterTypes.contains(Thread.UncaughtExceptionHandler.class)
                && parameterTypes.contains(Application.class);
    }

    private boolean checkForPreviousInstall() {
        final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (oldHandler != null
                && oldHandler.getClass().getName().startsWith(GracefulCrashConstants.GRACEFUL_CRASH_PACKAGE_NAME)) {
            Log.i(GracefulCrashConstants.TAG, GracefulCrashConstants.ALREADY_INSTALLED);
            return true;
        } else if (oldHandler != null
                && !oldHandler.getClass().getName().startsWith(GracefulCrashConstants.SYSTEM_HANDLER_PACKAGE_NAME)) {
            Log.e(GracefulCrashConstants.TAG, GracefulCrashConstants.INSTALL_OVERRIDE);
        } else if (oldHandler != null
                && oldHandler.getClass().getName().startsWith(GracefulCrashConstants.SYSTEM_HANDLER_PACKAGE_NAME)) {
            Log.i(GracefulCrashConstants.TAG, GracefulCrashConstants.DEFAULT_SYSTEM_HANDLER_INSTALLED);
        }
        return false;
    }

}
