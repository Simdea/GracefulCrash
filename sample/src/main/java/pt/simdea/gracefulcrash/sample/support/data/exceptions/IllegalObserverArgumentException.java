package pt.simdea.gracefulcrash.sample.support.data.exceptions;

import android.support.annotation.NonNull;

import lombok.NoArgsConstructor;
import pt.simdea.gracefulcrash.sample.models.mvpmodels.observable.Observable;

/**
 * Class meant to represent an IllegalArgumentException for Exceptions occurring
 * when an Observer instance is null. {@link Observable} for more information.
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public final class IllegalObserverArgumentException extends IllegalArgumentException {

    /**
     * Constructs a new {@code IllegalArgumentException} with the current stack trace and the specified detail message.
     * @param detailMessage {@link String} value representing the detail message for this exception.
     */
    public IllegalObserverArgumentException(@NonNull final String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with the current stack trace,
     * the specified detail message and the specified cause.
     * @param detailMessage {@link String} value representing the detail message for this exception.
     * @param throwable     {@link Throwable} instance representing the cause of this exception.
     */
    public IllegalObserverArgumentException(@NonNull final String detailMessage, @NonNull final Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with the current stack trace and the specified cause.
     * @param throwable {@link Throwable} instance representing the cause of this exception.
     */
    public IllegalObserverArgumentException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}
