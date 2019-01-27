package io.fries.result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
                .isThrownBy(() -> Result.error(null))
                .withNoCause()
                .withMessage("The error of a Result cannot be null");
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_create_an_ok_result_wrapping_the_supplied_value() {
        final Supplier<Object> supplier = mock(Supplier.class);
        given(supplier.get()).willReturn(value);

        final Result<Object> result = Result.of(supplier);

        assertThat(result).isEqualTo(Result.ok(value));
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_create_an_error_result_when_the_supplier_throws_an_exception() {
        final Supplier<Object> supplier = mock(Supplier.class);
        final Throwable error = mock(RuntimeException.class);
        given(supplier.get()).willThrow(error);

        final Result<Object> result = Result.of(supplier);

        assertThat(result).isEqualTo(Result.error(error));
    }

    @Test
    void should_throw_when_providing_a_null_supplier_reference() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Result.of((Supplier<Object>) null))
                .withNoCause()
                .withMessage("The value supplier cannot be null");
    }

    @Test
    void should_create_an_ok_result_when_the_nullable_value_is_not_null() {
        final Result<Object> result = Result.ofNullable(value);

        assertThat(result.isOk()).isTrue();
    }

    @Test
    void should_create_an_error_result_containing_a_null_pointer_exception_when_the_nullable_value_is_null() {
        final Result<Object> result = Result.ofNullable(null);

        final Throwable error = result.getError();

        assertThat(error)
                .isInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    void should_create_an_ok_result_containing_the_unwrapped_optional_value() {
        final Optional<Object> optional = Optional.of(value);

        final Result<Object> result = Result.of(optional);

        assertThat(result).isEqualTo(Result.ok(value));
    }

    @Test
    void should_create_an_error_result_when_unwrapping_an_empty_optional() {
        final Optional<Object> optional = Optional.empty();

        final Result<Object> result = Result.of(optional);

        final Throwable error = result.getError();
        assertThat(error)
                .isInstanceOf(NoSuchElementException.class)
                .hasNoCause()
                .hasMessage("No value present when unwrapping the optional");
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_create_an_ok_result_when_the_provided_value_is_not_null() {
        final Supplier<Throwable> errorSupplier = mock(Supplier.class);

        final Result<Object> result = Result.ofNullable(value, errorSupplier);

        verify(errorSupplier, never()).get();
        assertThat(result).isEqualTo(Result.ok(value));
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_create_an_error_result_when_the_provided_value_is_null() {
        final Supplier<Throwable> errorSupplier = mock(Supplier.class);
        final RuntimeException error = mock(RuntimeException.class);
        given(errorSupplier.get()).willReturn(error);

        final Result<Object> result = Result.ofNullable(null, errorSupplier);

        verify(errorSupplier).get();
        assertThat(result).isEqualTo(Result.error(error));
    }

    @Test
    void should_throw_when_providing_a_null_reference_as_the_error_supplier() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Result.ofNullable(value, null))
                .withNoCause()
                .withMessage("The error supplier cannot be null");
    }
}
