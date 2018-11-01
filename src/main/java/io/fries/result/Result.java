package io.fries.result;

import java.util.Objects;

class Result<T> {

    private final T value;
    private final Throwable error;

    private Result(final T value) {
        this(value, null);
    }

    private Result(final T value, final Throwable error) {
        this.value = value;
        this.error = error;
    }

    static <T> Result<T> ok(final T value) {
        Objects.requireNonNull(value);
        return new Result<>(value);
    }

    static <T> Result<T> error(final Throwable error) {
        return new Result<>(null, error);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Result result = (Result) o;
        return value == result.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Result{" +
                "value=" + value +
                '}';
    }
}
