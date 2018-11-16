package pt.simdea.gracefulcrash.data.exceptions;

import android.support.annotation.NonNull;

import lombok.NoArgsConstructor;

/**
 * Class meant to represent a RuntimeException for Exceptions occurring when the invalid Singleton instances are
 * attempted to be created.
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public final class ActivityTrackerInstantiationException extends RuntimeException {

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace and the specified detail message.
     * @param detailMessage {@link String} value representing the detail message for this exception.
     */
    public ActivityTrackerInstantiationException(@NonNull final String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace,
     * the specified detail message and the specified cause.
     * @param detailMessage {@link String} value representing the detail message for this exception.
     * @param throwable     {@link Throwable} instance representing the cause of this exception.
     */
    public ActivityTrackerInstantiationException(@NonNull final String detailMessage,
                                                 @NonNull final Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace and the specified cause.
     * @param throwable {@link Throwable} instance representing the cause of this exception.
     */
    public ActivityTrackerInstantiationException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}
