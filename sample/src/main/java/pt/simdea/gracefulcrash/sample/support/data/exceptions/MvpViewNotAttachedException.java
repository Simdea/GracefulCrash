package pt.simdea.gracefulcrash.sample.support.data.exceptions;

import android.support.annotation.NonNull;

import lombok.NoArgsConstructor;
import pt.simdea.gracefulcrash.sample.presenters.IPresenter;

/**
 * Class meant to represent a RuntimeException for Exceptions occurring
 * when the Mvp View instance is not attached to a Presenter. {@link IPresenter} for more information.
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public final class MvpViewNotAttachedException extends RuntimeException {

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace and the specified detail message.
     * @param detailMessage {@link String} value representing the detail message for this exception.
     */
    public MvpViewNotAttachedException(@NonNull final String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace,
     * the specified detail message and the specified cause.
     * @param detailMessage {@link String} value representing the detail message for this exception.
     * @param throwable     {@link Throwable} instance representing the cause of this exception.
     */
    public MvpViewNotAttachedException(@NonNull final String detailMessage, @NonNull final Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace and the specified cause.
     * @param throwable {@link Throwable} instance representing the cause of this exception.
     */
    public MvpViewNotAttachedException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}
