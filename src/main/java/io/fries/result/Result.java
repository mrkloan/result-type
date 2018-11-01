package io.fries.result;

import java.util.Objects;

abstract class Result {

    private Result() {
    }

    static <T> Result ok(final T value) {
        Objects.requireNonNull(value);
        return new Ok<>(value);
    }

    static <E extends Throwable> Result error(final E error) {
        Objects.requireNonNull(error);
        return new Error<>(error);
    }

    static <T> Result ofNullable(final T value) {
        return Objects.nonNull(value)
                ? new Ok<>(value)
                : new Error<>(new NullPointerException());
    }

    abstract boolean isError();

    private static class Ok<T> extends Result {

        private final T value;

        private Ok(final T value) {
            this.value = value;
        }

        @Override
        boolean isError() {
            return false;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Ok<?> ok = (Ok<?>) o;
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

    private static class Error<E extends Throwable> extends Result {

        private final E error;

        private Error(final E error) {
            this.error = error;
        }

        @Override
        boolean isError() {
            return true;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Error<?> error1 = (Error<?>) o;
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
