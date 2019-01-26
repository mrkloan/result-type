package io.fries.result;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

class Error<T> implements Result<T> {

    private final Throwable throwable;

    Error(final Throwable throwable) {
        this.throwable = throwable;
    }

    @SuppressWarnings("unchecked")
    private <E extends Throwable> T propagate(final Throwable throwable) throws E {
        throw (E) throwable;
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
    public void ifError(final Consumer<Throwable> consumer) {
        requireNonNull(consumer, "The error consumer cannot be null");
        consumer.accept(throwable);
    }

    @Override
    public Result<T> switchIfError(final Supplier<Result<T>> fallbackSupplier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <U> Result<U> map(final Function<? super T, ? extends U> mapper) {
        return new Error<>(throwable);
    }

    @Override
    public <U> Result<U> flatMap(final Function<? super T, Result<U>> mapper) {
        return new Error<>(throwable);
    }

    @Override
    public Result<T> mapError(final Function<Throwable, ? extends Throwable> mapper) {
        requireNonNull(mapper, "The error mapper cannot be null");
        return new Error<>(mapper.apply(throwable));
    }

    @Override
    public T get() {
        return propagate(throwable);
    }

    @Override
    public T getOrElse(final Supplier<T> supplier) {
        requireNonNull(supplier);
        return supplier.get();
    }

    @Override
    public Throwable getError() {
        return throwable;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Error<?> error = (Error<?>) o;
        return Objects.equals(throwable, error.throwable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(throwable);
    }

    @Override
    public String toString() {
        return "Error{" +
                "throwable=" + throwable +
                '}';
    }
}
