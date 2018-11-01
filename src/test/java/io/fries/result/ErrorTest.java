package io.fries.result;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ErrorTest {

    @Test
    public void should_create_an_error_result_wrapping_the_provided_error() {
        final String error = "Error";

        final Result<?, String> result = Result.error(error);

        assertThat(result).isEqualTo(Result.error("Error"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_providing_a_null_reference_to_an_error_result() {
        Result.error(null);
    }

    @Test
    public void should_be_true_when_the_result_is_an_error_result() {
        final Result<?, String> result = Result.error("Error");

        final boolean isError = result.isError();

        assertThat(isError).isTrue();
    }

    @Test
    public void should_be_false_when_the_result_is_an_error_result() {
        final Result<?, String> result = Result.error("Error");

        final boolean isOk = result.isOk();

        assertThat(isOk).isFalse();
    }

    @Test
    public void should_not_call_the_ok_consumer() {
        //noinspection unchecked
        final Consumer<String> consumer = (Consumer<String>) mock(Consumer.class);
        final Result<String, ?> result = Result.error(new Throwable());

        result.ifOk(consumer);

        verify(consumer, never()).accept(anyString());
    }

    @Test
    public void should_consume_the_value_of_an_error_result() {
        //noinspection unchecked
        final Consumer<String> consumer = (Consumer<String>) mock(Consumer.class);
        final Result<?, String> result = Result.error("Error");

        result.ifError(consumer);

        verify(consumer).accept("Error");
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_a_null_reference_is_provided_as_the_error_consumer() {
        final Result<?, String> result = Result.error("Error");

        result.ifError(null);
    }

    @Test
    public void should_get_the_same_result_when_trying_to_unary_map_an_error_result() {
        //noinspection unchecked
        final UnaryOperator<Integer> mapper = (UnaryOperator<Integer>) mock(UnaryOperator.class);
        final Result<Integer, String> initialResult = Result.error("Error");

        final Result<Integer, String> mappedResult = initialResult.map(mapper);

        verify(mapper, never()).apply(anyInt());
        assertThat(mappedResult).isEqualTo(initialResult);
    }

    @Test
    public void should_get_the_same_result_when_trying_to_function_map_an_error_result() {
        //noinspection unchecked
        final Function<Integer, String> mapper = (Function<Integer, String>) mock(Function.class);
        final Result<Integer, String> initialResult = Result.error("Error");

        final Result<String, String> mappedResult = initialResult.map(mapper);

        verify(mapper, never()).apply(anyInt());
        assertThat(mappedResult).isEqualTo(initialResult);
    }

    @Test
    public void should_get_the_same_result_when_trying_to_flat_map_an_error_result() {
        //noinspection unchecked
        final Function<Integer, Result<String, String>> mapper = (Function<Integer, Result<String, String>>) mock(Function.class);
        final Result<Integer, String> initialResult = Result.error("Error");

        final Result<String, String> mappedResult = initialResult.flatMap(mapper);

        verify(mapper, never()).apply(anyInt());
        assertThat(mappedResult).isEqualTo(initialResult);
    }

    @Test
    public void should_map_the_wrapped_error_to_another_type() {
        final int error = 1;
        //noinspection unchecked
        final Function<Integer, String> mapper = (Function<Integer, String>) mock(Function.class);
        final Result<String, Integer> initialResult = Result.error(error);

        when(mapper.apply(error)).thenReturn("1");
        final Result<String, String> mappedResult = initialResult.mapError(mapper);

        verify(mapper).apply(error);
        assertThat(mappedResult).isEqualTo(Result.error("1"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_the_provided_error_mapper_reference_is_null() {
        Result.error("Error").mapError(null);
    }
}