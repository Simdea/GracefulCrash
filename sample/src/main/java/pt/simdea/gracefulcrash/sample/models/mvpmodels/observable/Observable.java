package pt.simdea.gracefulcrash.sample.models.mvpmodels.observable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;
import pt.simdea.gracefulcrash.sample.support.data.exceptions.IllegalObserverArgumentException;
import pt.simdea.gracefulcrash.sample.support.data.exceptions.IllegalObserverStateException;
import pt.simdea.gracefulcrash.sample.support.data.structs.Optional;

/**
 * Class meant to define an Object, called the {@link Observable} (sometimes called Subject as well), which maintains a
 * list of its dependents, called {@link Observer}, and notifies them automatically of any state changes.
 *
 * This class employs the Observer Pattern.
 * This class is Thread-Safe.
 */
@SuppressWarnings("unused")
public class Observable {

    private final Object monitor = new Object(); // this is the object we will be synchronizing on ("the monitor")
    private Set<Observer> mObservers; // Observer Set

    /**
     * Procedure mean to add a new {@link Observer} which will be notified when {@link Observable} changes.
     * @param observer {@link Observer} instance representing the observer to register with this {@link Observable}.
     */
    public void registerObserver(@Nullable final Observer observer) {
        if (observer == null) {
            throw new IllegalObserverArgumentException(ExceptionConstants.MODEL_OBSERVABLE_ILLEGAL_ARGUMENT_ERROR);
        }

        synchronized (monitor) {
            if (mObservers == null) {
                mObservers = new HashSet<>(1);
            }
            mObservers.add(observer);
        }
    }

    /**
     * Procedure mean to remove an {@link Observer} which will no longer be notified when {@link Observable} changes.
     * @param observer {@link Observer} instance representing the observer to unregister with this {@link Observable}.
     */
    public void unregisterObserver(@Nullable final Observer observer) {
        if (observer == null) {
            throw new IllegalObserverArgumentException(ExceptionConstants.MODEL_OBSERVABLE_ILLEGAL_ARGUMENT_ERROR);
        }

        synchronized (monitor) {
            if (mObservers != null) {
                mObservers.remove(observer);
            }
        }
    }

    /**
     * Procedure meant to notify all currently registered {@link Observer}s about the {@link Observable}'s change.
     */
    public void notifyObservers(@NonNull final Optional event) {
        final Set<Observer> observersCopy;

        synchronized (monitor) {
            if (mObservers == null) {
                throw new IllegalObserverStateException(ExceptionConstants.MODEL_OBSERVABLE_ILLEGAL_STATE_ERROR);
            } else {
                observersCopy = new HashSet<>(mObservers);
            }
        }

        for (final Observer observer : observersCopy) {
            observer.onObservableChanged(event); // TODO: Can we solve this warning?
        }
    }

}
