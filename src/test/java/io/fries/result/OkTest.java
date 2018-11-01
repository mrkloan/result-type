package io.fries.result;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OkTest {

    @Test
    public void should_create_an_ok_result_wrapping_the_provided_value() {
        final String value = "Value";

        final Result<String, ?> result = Result.ok(value);

        assertThat(result).isEqualTo(Result.ok("Value"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_providing_a_null_reference_to_an_ok_result() {
        Result.ok(null);
    }

    @Test
    public void should_be_false_when_the_result_is_an_ok_result() {
        final Result result = Result.ok("Value");

        final boolean isError = result.isError();

        assertThat(isError).isFalse();
    }

    @Test
    public void should_be_true_when_the_result_is_an_ok_result() {
        final Result result = Result.ok("Value");

        final boolean isOk = result.isOk();

        assertThat(isOk).isTrue();
    }

    @Test
    public void should_consume_the_value_of_an_ok_result() {
        //noinspection unchecked
        final Consumer<String> consumer = (Consumer<String>) mock(Consumer.class);
        final Result<String, ?> result = Result.ok("Value");

        result.ifOk(consumer);

        verify(consumer).accept("Value");
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_a_null_reference_is_provided_as_the_ok_consumer() {
        final Result<String, ?> result = Result.ok("Value");

        result.ifOk(null);
    }

    @Test
    public void should_not_call_the_error_consumer() {
        //noinspection unchecked
        final Consumer<String> consumer = (Consumer<String>) mock(Consumer.class);
        final Result<?, String> result = Result.ok("Value");

        result.ifError(consumer);

        verify(consumer, never()).accept(anyString());
    }

    @Test
    public void should_map_the_wrapped_value_without_changing_its_type() {
        final int value = 1;
        final int expectedValue = 2;
        //noinspection unchecked
        final UnaryOperator<Integer> mapper = (UnaryOperator<Integer>) mock(UnaryOperator.class);
        final Result<Integer, ?> initialResult = Result.ok(value);

        when(mapper.apply(value)).thenReturn(expectedValue);
        final Result<Integer, ?> mappedResult = initialResult.map(mapper);

        verify(mapper).apply(value);
        assertThat(mappedResult).isEqualTo(Result.ok(expectedValue));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_the_provided_mapper_reference_is_null_for_unary_operator_mapping() {
        Result.ok(1).map(null);
    }

    @Test
    public void should_map_the_wrapped_value_to_another_type() {
        final int value = 1;
        final String expectedValue = "1";
        //noinspection unchecked
        final Function<Integer, String> mapper = (Function<Integer, String>) mock(Function.class);
        final Result<Integer, ?> initialResult = Result.ok(value);

        when(mapper.apply(value)).thenReturn(expectedValue);
        final Result<String, ?> mappedResult = initialResult.map(mapper);

        verify(mapper).apply(value);
        assertThat(mappedResult).isEqualTo(Result.ok(expectedValue));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_the_provided_mapper_reference_is_null_for_function_mapping() {
        final Function<Integer, String> mapper = null;
        Result.ok(1).map(mapper);
    }

    @Test
    public void should_flat_map_the_wrapped_result() {
        final int value = 1;
        //noinspection unchecked
        final Function<Integer, Result<String, String>> mapper = (Function<Integer, Result<String, String>>) mock(Function.class);
        final Result<Integer, String> initialResult = Result.ok(value);

        when(mapper.apply(value)).thenReturn(Result.ok("1"));
        final Result<String, String> mappedResult = initialResult.flatMap(mapper);

        verify(mapper).apply(value);
        assertThat(mappedResult).isEqualTo(Result.ok("1"));
    }
}