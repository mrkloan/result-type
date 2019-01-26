package io.fries.result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OkTest {

    @Mock
    private Throwable value;

    private Result<Object> result;

    @BeforeEach
    void setUp() {
        this.result = new Ok<>(value);
    }

    @Test
    void should_be_true_when_the_result_is_an_ok_result() {
        final boolean isOk = result.isOk();

        assertThat(isOk).isTrue();
    }

    @Test
    void should_be_false_when_the_result_is_an_ok_result() {
        final boolean isError = result.isError();

        assertThat(isError).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_consume_the_value_of_an_ok_result() {
        final Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class);

        result.ifOk(consumer);

        verify(consumer).accept(value);
    }

    @Test
    void should_throw_when_a_null_reference_is_provided_as_the_ok_consumer() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> result.ifOk(null))
                .withNoCause()
                .withMessage("The value consumer cannot be null");
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_not_call_the_error_consumer() {
        final Consumer<Throwable> consumer = (Consumer<Throwable>) mock(Consumer.class);

        result.ifError(consumer);

        verify(consumer, never()).accept(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_map_the_wrapped_value_to_another_type() {
        final Object mappedValue = mock(Object.class);
        final Function<Object, Object> mapper = (Function<Object, Object>) mock(Function.class);
        given(mapper.apply(value)).willReturn(mappedValue);

        final Result<Object> mappedResult = result.map(mapper);

        verify(mapper).apply(value);
        assertThat(mappedResult).isEqualTo(new Ok<>(mappedValue));
    }

    @Test
    void should_throw_when_the_provided_mapper_reference_is_null() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> result.map(null))
                .withNoCause()
                .withMessage("The value mapper cannot be null");
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_flat_map_the_wrapped_result() {
        final Object mappedValue = mock(Object.class);
        final Function<Object, Result<Object>> mapper = (Function<Object, Result<Object>>) mock(Function.class);
        given(mapper.apply(value)).willReturn(new Ok<>(mappedValue));

        final Result<Object> mappedResult = result.flatMap(mapper);

        verify(mapper).apply(value);
        assertThat(mappedResult).isEqualTo(new Ok<>(mappedValue));
    }

    @Test
    void should_throw_when_the_provided_flat_mapper_reference_is_null() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> result.flatMap(null))
                .withNoCause()
                .withMessage("The value flat-mapper cannot be null");
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_get_the_same_result_when_trying_to_map_an_error_result() {
        final Function<Throwable, Throwable> mapper = (Function<Throwable, Throwable>) mock(Function.class);

        final Result<Object> mappedResult = result.mapError(mapper);

        verify(mapper, never()).apply(any());
        assertThat(mappedResult).isEqualTo(result);
    }

    @Test
    void should_get_the_wrapped_value() {
        final Object unwrappedValue = result.get();

        assertThat(unwrappedValue).isEqualTo(value);
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_get_the_wrapped_value_and_not_the_supplied_fallback() {
        final Supplier<Object> supplier = (Supplier<Object>) mock(Supplier.class);

        final Object unwrappedValue = result.getOrElse(supplier);

        verify(supplier, never()).get();
        assertThat(unwrappedValue).isEqualTo(value);
    }

    @Test
    void should_throw_when_trying_to_unwrap_the_error() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(result::getError)
                .withNoCause()
                .withMessage("Result is ok");
    }
}