package pt.simdea.gracefulcrash.sample.models.androidmodel.mvpimpl;

import javax.inject.Inject;
import javax.inject.Singleton;

import pt.simdea.gracefulcrash.sample.support.data.structs.Optional;

/**
 * Class responsible for the Main / Home Screen' Data Model.
 */
@Singleton
public class DataModelMvp extends BaseModelMvp {

    /**
     * Instantiates a new {@link DataModelMvp}.
     */
    @Inject
    public DataModelMvp() {
        /* Add dependencies here. */
    }

    /** {@inheritDoc} */
    @Override
    public void cancelPendingWork() {
        /* Cleanup any pending work here. */
    }

    /**
     * TODO: Temp...
     */
    public void doSomething() {
       notifyObservers(Optional.just("Did something..."));
    }

    // Add more logic in the future...

}
