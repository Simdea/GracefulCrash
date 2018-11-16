package pt.simdea.gracefulcrash.sample.models.mvpmodels.observable;

import android.support.annotation.NonNull;

import pt.simdea.gracefulcrash.sample.support.data.structs.Optional;

/**
 * Interface meant to define an Object, called the {@link Observer}, which will be notified of any changes occurring
 * to a given {@link Observable} object that it is registered to (i.e. observing).
 *
 * This class employs the Observer Pattern.
 *
 * @param <T> {@link Optional} value representing the generic event data to be passed on to the observers.
 */
public interface Observer<T> {

    /**
     * Procedure meant to trigger a notification when the target {@link Observable} object changes.
     * @param notification {@link Optional<T>} instance representing the {@link Optional} struct with the
     *                     notifications desired data.
     */
    void onObservableChanged(@NonNull final Optional<T> notification);

}
