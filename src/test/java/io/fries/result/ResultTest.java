package io.fries.result;

import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest {

    @Test
    public void should_create_an_ok_result_when_the_provided_value_is_not_null() {
        final String value = "Value";
        final Supplier<Throwable> errorSupplier = NullPointerException::new;

        final Result<?, ?> result = Result.ofNullable(value, errorSupplier);

        assertThat(result).isEqualTo(Result.ok("Value"));
    }

    @Test
    public void should_create_an_error_result_when_the_provided_value_is_null() {
        final Supplier<NullPointerException> errorSupplier = NullPointerException::new;

        final Result<?, ?> result = Result.ofNullable(null, errorSupplier);

        assertThat(result.isError()).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_providing_a_null_reference_as_the_error_supplier() {
        final String value = "Value";

        Result.ofNullable(value, null);
    }
}
