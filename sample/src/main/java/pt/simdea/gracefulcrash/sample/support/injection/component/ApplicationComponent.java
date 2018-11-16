package pt.simdea.gracefulcrash.sample.support.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import pt.simdea.gracefulcrash.sample.support.bootstrap.SampleApplication;
import pt.simdea.gracefulcrash.sample.support.injection.module.ApplicationModule;
import pt.simdea.gracefulcrash.sample.support.injection.scope.ApplicationContext;

/**
 * Component responsible for connecting the dependency consumer and producer, at the Application level.
 * Defines the connection between the provider of objects (modules) and the objects which express a dependency.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    /**
     * Procedure responsible for injecting a {@link SampleApplication} instance.
     *
     * NOTE: When the dependencies are provided through field injection i.e. @inject on the member variables,
     * we have to tell the Dagger to scan this class through the implementation of this interface.
     *
     * @param application the SampleApplication instance to be injected / scanned by Dagger.
     */
    void inject(final SampleApplication application);

    /**
     * Procedure responsible for injecting the Application level {@link Context} instance.
     * @return the Application level Context instance.
     */
    @ApplicationContext
    Context getContext();

    /**
     * Procedure responsible for injecting the Application instance {@link Application}.
     * @return the Application instance.
     */
    Application getApplication();

    // Add remaining exposures here...

}
