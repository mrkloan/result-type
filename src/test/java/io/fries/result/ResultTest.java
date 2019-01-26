package io.fries.result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
class ResultTest {

    @Mock
    private Object value;

    @Mock
    private Throwable error;

    @Test
    void should_create_an_ok_result_wrapping_the_provided_value() {
        final Result<Object> result = Result.ok(value);

        assertThat(result).isEqualTo(Result.ok(value));
    }

    @Test
    void should_throw_when_providing_a_null_reference_to_an_ok_result() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Result.ok(null))
                .withNoCause()
                .withMessage("The value of a Result cannot be null");
    }

    @Test
    void should_create_an_error_result_wrapping_the_provided_error() {
        final Result<Object> result = Result.error(error);

        assertThat(result).isEqualTo(Result.error(error));
    }

    @Test
    void should_throw_when_providing_a_null_reference_to_an_error_result() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Result.error(null));
    }

    @Test
    void should_create_an_ok_result_when_the_provided_value_is_not_null() {
        final Supplier<Throwable> errorSupplier = NullPointerException::new;

        final Result<Object> result = Result.ofNullable(value, errorSupplier);

        assertThat(result).isEqualTo(Result.ok(value));
    }

    @Test
    void should_create_an_error_result_when_the_provided_value_is_null() {
        final Supplier<NullPointerException> errorSupplier = NullPointerException::new;

        final Result<Object> result = Result.ofNullable(null, errorSupplier);

        assertThat(result.isError()).isTrue();
    }

    @Test
    void should_throw_when_providing_a_null_reference_as_the_error_supplier() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Result.ofNullable(value, null));
    }
}
