package io.fries.result;

import org.junit.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResultTest {

    @Test
    public void should_create_an_ok_result_instance_wrapping_an_integer() {
        final int value = 1;

        final Result<Integer> result = Result.ok(value);

        assertThat(result).isEqualTo(Result.ok(1));
    }

    @Test
    public void should_create_an_ok_result_instance_wrapping_a_long() {
        final long value = 1L;

        final Result<Long> result = Result.ok(value);

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
        final String value = "Value";

        final Result<String> result = Result.ofNullable(value);

        assertThat(result).isEqualTo(Result.ok("Value"));
    }

    @Test
    public void should_create_an_error_result_when_the_provided_value_is_null() {
        final Object value = null;

        final Result result = Result.ofNullable(value);

        assertThat(result.isError()).isTrue();
    }

    @Test
    public void should_be_true_when_the_result_is_an_error_result() {
        final Result result = Result.error(new Throwable());

        final boolean isError = result.isError();

        assertThat(isError).isTrue();
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
    public void should_be_false_when_the_result_is_an_error_result() {
        final Result result = Result.error(new Throwable());

        final boolean isOk = result.isOk();

        assertThat(isOk).isFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_consume_the_value_of_an_ok_result() {
        final Consumer<String> consumer = (Consumer<String>) mock(Consumer.class);
        final Result<String> result = Result.ok("Value");

        result.ifOk(consumer);

        verify(consumer).accept("Value");
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_a_null_reference_is_provided_as_the_ok_consumer() {
        final Result<String> result = Result.ok("Value");

        result.ifOk(null);
    }
}
