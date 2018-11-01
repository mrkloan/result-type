package io.fries.result;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}