package pt.simdea.gracefulcrash.sample.support.injection.component;

import dagger.Component;
import pt.simdea.gracefulcrash.sample.models.androidmodel.mvpimpl.DataModelMvp;
import pt.simdea.gracefulcrash.sample.presenters.activity.HomePresenter;
import pt.simdea.gracefulcrash.sample.support.injection.module.ActivityModule;
import pt.simdea.gracefulcrash.sample.support.injection.scope.PerActivity;

/**
 * Component responsible for connecting the dependency consumer and producer, at the Activity level.
 * Defines the connection between the provider of objects (modules) and the objects which express a dependency.
 */
@SuppressWarnings("unused")
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    /**
     * Procedure responsible for injecting a BaseActivity instance {@link HomePresenter}.
     *
     * When the dependencies are provided through field injection i.e. @inject on the member variables,
     * we have to tell the Dagger to scan this class through the implementation of this interface.
     *
     * @param homePresenter the HomePresenter Activity instance to be injected / scanned by Dagger.
     */
    void inject(final HomePresenter homePresenter);

    // Add remaining injections here...

    /* ############################################ Component Exposure ############################################ */

    /**
     * Procedure responsible for injecting the Activity level Data Model instance {@link DataModelMvp}.
     * @return the Activity level Data Model instance.
     */
    DataModelMvp getHomeModel();

    // Add remaining exposures here...

}
