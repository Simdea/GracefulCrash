package pt.simdea.gracefulcrash.sample.support.bootstrap;

import android.app.Application;
import android.support.annotation.NonNull;

import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;
import pt.simdea.gracefulcrash.sample.support.injection.component.ApplicationComponent;
import pt.simdea.gracefulcrash.sample.support.injection.component.DaggerApplicationComponent;
import pt.simdea.gracefulcrash.sample.support.injection.module.ApplicationModule;

/**
 * Utility class meant to handle external Library initializations.
 */
final class LibraryInitializer {

    /**
     * Instantiates a new LibraryInitializer. Private to prevent instantiation.
     */
    private LibraryInitializer() {
        throw new AssertionError(ExceptionConstants.ASSERTION_ERROR); // Throw an exception if this *is* ever called
    }

    /**
     * Procedure meant to configure the Dagger2 library.
     * @see <a href="Dagger2">https://google.github.io/dagger/</a>
     * @param application {@link Application} value representing the Application instance.
     * @return            {@link ApplicationComponent} value representing the Dagger Application Component instance.
     */
    static ApplicationComponent initializeDagger(@NonNull final Application application) {
        // Dagger%COMPONENT_NAME%
        return DaggerApplicationComponent
                .builder()
                // list of modules that are part of this component need to be created here too
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    // Add library initializations here...

}
