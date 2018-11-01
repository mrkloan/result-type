package io.fries.result;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class Result<T, E> {

    private Result() {
    }

    public static <T, E> Result<T, E> ok(final T value) {
        Objects.requireNonNull(value);
        return new Ok<>(value);
    }

    public static <T, E> Result<T, E> error(final E error) {
        Objects.requireNonNull(error);
        return new Error<>(error);
    }

    public static <T, E> Result<T, E> ofNullable(final T value, final Supplier<E> errorSupplier) {
        Objects.requireNonNull(errorSupplier);

        return Objects.nonNull(value)
                ? ok(value)
                : error(errorSupplier.get());
    }

    public abstract boolean isOk();

    public abstract void ifOk(final Consumer<T> consumer);

    public abstract boolean isError();

    public abstract void ifError(final Consumer<E> consumer);

    public abstract Result<T, E> map(final UnaryOperator<T> mapper);

    public abstract <U> Result<U, E> map(final Function<? super T, ? extends U> mapper);

    public abstract <U> Result<U, E> flatMap(final Function<? super T, Result<U, E>> mapper);

    public abstract <F> Result<T, F> mapError(final Function<E, F> mapper);

    public abstract T get();

    private static class Ok<T, E> extends Result<T, E> {

        private final T value;

        private Ok(final T value) {
            this.value = value;
        }

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public void ifOk(final Consumer<T> consumer) {
            Objects.requireNonNull(consumer);
            consumer.accept(value);
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public void ifError(final Consumer<E> consumer) {
        }

        @Override
        public Result<T, E> map(final UnaryOperator<T> mapper) {
            Objects.requireNonNull(mapper);
            return ok(mapper.apply(value));
        }

        @Override
        public <U> Result<U, E> map(final Function<? super T, ? extends U> mapper) {
            Objects.requireNonNull(mapper);
            return ok(mapper.apply(value));
        }

        @Override
        public <U> Result<U, E> flatMap(final Function<? super T, Result<U, E>> mapper) {
            Objects.requireNonNull(mapper);
            return mapper.apply(value);
        }

        @Override
        public <F> Result<T, F> mapError(final Function<E, F> mapper) {
            return ok(value);
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Ok<?, ?> ok = (Ok<?, ?>) o;
            return Objects.equals(value, ok.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Ok{" +
                    "value=" + value +
                    '}';
        }
    }

    private static class Error<T, E> extends Result<T, E> {

        private final E error;

        private Error(final E error) {
            this.error = error;
        }

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public void ifOk(final Consumer<T> consumer) {
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
        public Result<T, E> map(final UnaryOperator<T> mapper) {
            return this;
        }

        @Override
        public <U> Result<U, E> map(final Function<? super T, ? extends U> mapper) {
            return error(error);
        }

        @Override
        public <U> Result<U, E> flatMap(final Function<? super T, Result<U, E>> mapper) {
            return error(error);
        }

        @Override
        public <F> Result<T, F> mapError(final Function<E, F> mapper) {
            Objects.requireNonNull(mapper);
            return error(mapper.apply(error));
        }

        @Override
        public T get() {
            throw new NoSuchElementException("Result is an error");
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Error<?, ?> error1 = (Error<?, ?>) o;
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
}
