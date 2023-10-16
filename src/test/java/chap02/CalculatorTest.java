package chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorTest {
    @Test
    void plus() {
        int sum = Calculator.plus(1, 2);
        assertEquals(3, sum);
    }
}
