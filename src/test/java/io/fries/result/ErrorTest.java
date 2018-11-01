package io.fries.result;

import org.junit.Test;

import java.util.function.Consumer;

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
}