package io.fries.result;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, E> {

    static <T, E> Result<T, E> ok(final T value) {
        Objects.requireNonNull(value);
        return new Ok<>(value);
    }

    static <T, E> Result<T, E> error(final E error) {
        Objects.requireNonNull(error);
        return new Error<>(error);
    }

    static <T, E> Result<T, E> ofNullable(final T value, final Supplier<E> errorSupplier) {
        Objects.requireNonNull(errorSupplier);

        return Objects.nonNull(value)
                ? ok(value)
                : error(errorSupplier.get());
    }

    boolean isOk();

    void ifOk(final Consumer<T> consumer);

    boolean isError();

    void ifError(final Consumer<E> consumer);

    <U> Result<U, E> map(final Function<? super T, ? extends U> mapper);

    <U> Result<U, E> flatMap(final Function<? super T, Result<U, E>> mapper);

    <F> Result<T, F> mapError(final Function<E, F> mapper);

    T get();

    T getOrElse(final Supplier<T> supplier);

    E getError();
}
