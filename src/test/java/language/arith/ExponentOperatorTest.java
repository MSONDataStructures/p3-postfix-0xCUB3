package language.arith;

import language.Operand;
import language.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExponentOperatorTest {
    private Operator<Integer> operator;

    @BeforeEach
    public void setup() {
        operator = new ExponentOperator();
    }

    @Test
    public void testPerformOperation() {
        operator.setOperand(0, new Operand<>(2));
        operator.setOperand(1, new Operand<>(3));
        assertEquals(8, operator.performOperation().getValue());
    }

    @Test
    public void testZeroExponent() {
        operator.setOperand(0, new Operand<>(5));
        operator.setOperand(1, new Operand<>(0));
        assertEquals(1, operator.performOperation().getValue());
    }

    @Test
    public void testOneAsBase() {
        operator.setOperand(0, new Operand<>(1));
        operator.setOperand(1, new Operand<>(5));
        assertEquals(1, operator.performOperation().getValue());
    }

    @Test
    public void testNegativeBase() {
        operator.setOperand(0, new Operand<>(-2));
        operator.setOperand(1, new Operand<>(3));
        assertEquals(-8, operator.performOperation().getValue());
    }

    @Test
    public void testNegativeExponent() {
        operator.setOperand(0, new Operand<>(2));
        assertThrows(IllegalStateException.class,
                () -> operator.setOperand(1, new Operand<>(-1)));
    }

    @Test
    public void testPriority() {
        assertEquals(3, ((ExponentOperator)operator).getPriority());
    }
}