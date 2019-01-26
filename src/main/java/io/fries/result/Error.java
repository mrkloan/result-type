package io.fries.result;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class Error<T, E> implements Result<T, E> {

    private final E error;

    Error(final E error) {
        this.error = error;
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public void ifOk(final Consumer<T> consumer) {
        // Do nothing when trying to consume the value of an Error result.
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public void ifError(final Consumer<E> consumer) {
        Objects.requireNonNull(consumer);
        consumer.accept(error);
    }

    @Override
    public <U> Result<U, E> map(final Function<? super T, ? extends U> mapper) {
        return new Error<>(error);
    }

    @Override
    public <U> Result<U, E> flatMap(final Function<? super T, Result<U, E>> mapper) {
        return new Error<>(error);
    }

    @Override
    public <F> Result<T, F> mapError(final Function<E, F> mapper) {
        Objects.requireNonNull(mapper);
        return new Error<>(mapper.apply(error));
    }

    @Override
    public T get() {
        throw new NoSuchElementException("Result is an error");
    }

    @Override
    public T getOrElse(final Supplier<T> supplier) {
        Objects.requireNonNull(supplier);
        return supplier.get();
    }

    @Override
    public E getError() {
        return error;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final io.fries.result.Error<?, ?> error1 = (io.fries.result.Error<?, ?>) o;
        return Objects.equals(error, error1.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error +
                '}';
    }
}
