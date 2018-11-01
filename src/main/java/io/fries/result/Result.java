package io.fries.result;

import java.util.Objects;

class Result<T> {

    private final T value;

    private Result(final T value) {
        this.value = value;
    }

    static <T> Result<T> ok(final T value) {
        return new Result<>(value);
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
