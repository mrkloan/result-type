package io.fries.result;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

abstract class Result<T, E> {

    private Result() {
    }

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

    abstract boolean isOk();

    abstract void ifOk(final Consumer<T> consumer);

    abstract boolean isError();

    abstract void ifError(final Consumer<E> consumer);

    abstract Result<T, E> map(final UnaryOperator<T> mapper);

    private static class Ok<T, E> extends Result<T, E> {

        private final T value;

        private Ok(final T value) {
            this.value = value;
        }

        @Override
        boolean isOk() {
            return true;
        }

        @Override
        void ifOk(final Consumer<T> consumer) {
            Objects.requireNonNull(consumer);
            consumer.accept(value);
        }

        @Override
        boolean isError() {
            return false;
        }

        @Override
        void ifError(final Consumer<E> consumer) {
        }

        @Override
        Result<T, E> map(final UnaryOperator<T> mapper) {
            Objects.requireNonNull(mapper);
            return ok(mapper.apply(value));
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
        boolean isOk() {
            return false;
        }

        @Override
        void ifOk(final Consumer<T> consumer) {
        }

        @Override
        boolean isError() {
            return true;
        }

        @Override
        void ifError(final Consumer<E> consumer) {
            Objects.requireNonNull(consumer);
            consumer.accept(error);
        }

        @Override
        Result<T, E> map(final UnaryOperator<T> mapper) {
            return this;
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
