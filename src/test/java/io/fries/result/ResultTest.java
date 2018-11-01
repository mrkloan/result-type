package io.fries.result;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest {

    @Test
    public void should_create_an_ok_result_instance_wrapping_an_integer() {
        final Result result = Result.ok(1);

        assertThat(result).isEqualTo(Result.ok(1));
    }

    @Test
    public void should_create_an_ok_result_instance_wrapping_a_long() {
        final Result result = Result.ok(1L);

        assertThat(result).isEqualTo(Result.ok(1L));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_providing_a_null_reference_to_an_ok_result() {
        Result.ok(null);
    }

    @Test
    public void should_create_an_error_result_instance_wrapping_a_throwable() {
        final Throwable error = new Throwable();

        final Result result = Result.error(error);

        assertThat(result).isEqualTo(Result.error(error));
    }

    @Test
    public void should_create_an_error_result_instance_wrapping_an_exception() {
        final Exception error = new Exception();

        final Result result = Result.error(error);

        assertThat(result).isEqualTo(Result.error(error));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_providing_a_null_reference_to_an_error_result() {
        Result.error(null);
    }

    @Test
    public void should_create_an_ok_result_when_the_provided_value_is_not_null() {
        final Result result = Result.ofNullable("Value");

        assertThat(result).isEqualTo(Result.ok("Value"));
    }

    @Test
    public void should_create_an_error_result_when_the_provided_value_is_null() {
        final Result result = Result.ofNullable(null);

        assertThat(result.isError()).isTrue();
    }

    @Test
    public void should_be_true_when_the_result_is_an_error_result() {
        final Result result = Result.error(new Throwable());

        assertThat(result.isError()).isTrue();
    }

    @Test
    public void should_be_false_when_the_result_is_an_ok_result() {
        final Result result = Result.ok("Value");

        assertThat(result.isError()).isFalse();
    }

    @Test
    public void should_be_true_when_the_result_is_an_ok_result() {
        final Result result = Result.ok("Value");

        assertThat(result.isOk()).isTrue();
    }

    @Test
    public void should_be_false_when_the_result_is_an_error_result() {
        final Result result = Result.error(new Throwable());

        assertThat(result.isOk()).isFalse();
    }
}
