package pt.simdea.gracefulcrash.sample.models.mvpmodels;

import android.support.annotation.NonNull;

import pt.simdea.gracefulcrash.sample.models.androidmodel.mvpimpl.DataModelMvp;
import pt.simdea.gracefulcrash.sample.models.mvpmodels.observable.Observable;
import pt.simdea.gracefulcrash.sample.models.mvpmodels.observable.Observer;
import pt.simdea.gracefulcrash.sample.support.data.structs.Optional;

/**
 * Interface meant to define an {@link Observer} for the {@link DataModelMvp} {@link Observable} object, which will be
 * notified of any changes occurring to it.
 *
 * This class employs the Observer Pattern.
 */
public interface IDataModelObserver extends Observer<String> {

    /**
     * Procedure meant to trigger a notification when the target {@link Observable} object changes.
     * @param notification {@link Optional <String>} instance representing the {@link Optional} struct with the
     *                     notifications desired data.
     */
    void onObservableChanged(@NonNull final Optional<String> notification);

}
