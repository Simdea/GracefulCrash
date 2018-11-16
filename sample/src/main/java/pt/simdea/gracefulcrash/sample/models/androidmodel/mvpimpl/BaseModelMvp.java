package pt.simdea.gracefulcrash.sample.models.androidmodel.mvpimpl;


import pt.simdea.gracefulcrash.sample.models.mvpmodels.observable.Observable;

/**
 * Class responsible for the Base Data Model logic.
 */
public abstract class BaseModelMvp extends Observable {

    /**
     * Procedure responsible for cancelling any pending network requests.
     */
    public abstract void cancelPendingWork();

    // Add more logic in the future...

}
