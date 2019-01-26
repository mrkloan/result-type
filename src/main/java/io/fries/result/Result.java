package io.fries.result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public interface Result<T> {

    static <T> Result<T> ok(final T value) {
        requireNonNull(value, "The value of a Result cannot be null");
        return new Ok<>(value);
    }

    static <T, E extends Throwable> Result<T> error(final E throwable) {
        requireNonNull(throwable, "The error of a Result cannot be null");
        return new Error<>(throwable);
    }

    static <T> Result<T> of(final Supplier<T> supplier) {
        requireNonNull(supplier, "The value supplier cannot be null");

        try {
            return ok(supplier.get());
        } catch (final Exception error) {
            return error(error);
        }
    }

    static <T> Result<T> ofNullable(final T value, final Supplier<? extends Throwable> errorSupplier) {
        requireNonNull(errorSupplier, "The error supplier cannot be null");

        return nonNull(value)
                ? ok(value)
                : error(errorSupplier.get());
    }

    boolean isOk();

    void ifOk(final Consumer<T> consumer);

    boolean isError();

    void ifError(final Consumer<Throwable> consumer);

    Result<T> switchIfError(final Function<Throwable, Result<T>> fallbackMethod);

    <U> Result<U> map(final Function<? super T, ? extends U> mapper);

    <U> Result<U> flatMap(final Function<? super T, Result<U>> mapper);

    Result<T> mapError(final Function<Throwable, ? extends Throwable> mapper);

    T get();

    T getOrElse(final Supplier<T> supplier);

    Throwable getError();
}
