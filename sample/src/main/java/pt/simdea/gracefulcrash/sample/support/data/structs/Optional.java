package pt.simdea.gracefulcrash.sample.support.data.structs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;
import pt.simdea.gracefulcrash.sample.support.data.exceptions.UndefinedOptionalException;

/**
 * Utility class responsible for helping with {@link NullPointerException} prevention.
 * It works similarly to Java 8 {@link java.util.Optional}.
 *
 * Its main goal is to prevent code from throwing {@link NullPointerException}'s in a more code friendly manner,
 * without nested checks, boilerplate code and general lack of readability.
 *
 * @param <T> the desired {@link Optional}'s value type.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Optional<T> {

    public static final Optional<?> NOTHING = new Optional<>(null);
    private final T mValue;

    /**
     * Instantiates a new {@link Optional}.
     * @param value {@link T} instance representing the {@link Optional}'s value.
     */
    private Optional(@Nullable final T value) {
        mValue = value;
    }

    /**
     * Procedure responsible for defining an {@link Optional}.
     * @param value {@link T} instance representing the desired {@link Optional}'s value.
     * @return      {@link Optional} instance containing the desired {@link Object} value or {@link Optional#NOTHING}.
     */
    public static <T> Optional<T> nullIsNothing(@Nullable final T value) {
        return value == null ? noValue() : just(value);
    }

    /**
     * Procedure responsible for defining an {@link Optional} for the desired {@link Object} value.
     * @param value {@link T} instance representing the desired {@link Optional}'s value.
     * @return      {@link Optional} instance containing the desired {@link Object} value.
     */
    public static <T> Optional<T> just(@NonNull final T value) {
        return new Optional<>(value);
    }

    /**
     * Procedure responsible for defining an {@link Optional} with the default {@link Optional#NOTHING} value.
     * @return {@link Optional} instance containing the default {@link Optional#NOTHING} value.
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> noValue() {
        return (Optional<T>) NOTHING;
    }

    /**
     * Procedure responsible for querying if this {@link Optional} instance is defined or not.
     * An {@link Optional} is defined when {@link Optional#mValue} is not null and not {@link Optional#NOTHING}.
     * @return {@link Boolean} value representing whether {@link Optional} instance is defined or not.
     */
    public boolean isDefined() {
        return this != NOTHING;
    }

    /**
     * Procedure responsible for retrieving this {@link Optional}'s value.
     * @return {@link T} instance representing this {@link Optional}'s value.
     * @throws RuntimeException in case this {@link Optional}'s value is undefined.
     */
    public T get() {
        if (isDefined()) {
            return mValue;
        }
        throw new UndefinedOptionalException(ExceptionConstants.OPTIONAL_VALUE_UNDEFINED);
    }

    /**
     * Procedure responsible for retrieving this {@link Optional}'s value. If this {@link Optional}'s value is
     * undefined, defaultValue will be returned.
     * @param defaultValue {@link T} instance representing this {@link Optional}'s desired default value.
     * @return             {@link T} instance representing this {@link Optional}'s value or the desired default value.
     */
    public T or(@Nullable final T defaultValue) {
        return isDefined() ? mValue : defaultValue;
    }

}
