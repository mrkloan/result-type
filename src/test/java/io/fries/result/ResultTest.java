package io.fries.result;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest {

    @Test
    public void should_create_an_ok_result_instance_wrapping_an_integer() {
        final Result result = Result.ok(1);

        assertThat(result).isEqualTo(Result.ok(1));
    }
}
