package evaluator.arith;

import evaluator.Evaluator;
import evaluator.IllegalPostfixExpressionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithInfixEvaluatorTest {
    private Evaluator<Integer> evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new ArithInfixEvaluator();
    }

    @Test
    void testSimpleAddition() {
        assertEquals(5, evaluator.evaluate("2 + 3"));
    }

    @Test
    void testPrecedence() {
        assertEquals(14, evaluator.evaluate("2 + 3 * 4"));
        assertEquals(20, evaluator.evaluate("(2 + 3) * 4"));
    }

    @Test
    void testExponentiation() {
        assertEquals(8, evaluator.evaluate("2 ^ 3"));
        assertEquals(32, evaluator.evaluate("2 ^ 3 * 4"));
        assertEquals(512, evaluator.evaluate("2 ^ 3 ^ 2"));
    }

    @Test
    void testComplexExpression() {
        assertEquals(53, evaluator.evaluate("3 * 4 ^ 2 + 5"));
    }

    @Test
    void testParentheses() {
        assertEquals(100, evaluator.evaluate("(2 + 3) ^ 2 * 4"));
    }

    @Test
    void testInvalidExpression() {
        assertThrows(IllegalPostfixExpressionException.class,
                () -> evaluator.evaluate("2 + + 3"));
    }

    @Test
    void testMismatchedParentheses() {
        assertThrows(IllegalPostfixExpressionException.class,
                () -> evaluator.evaluate("(2 + 3"));
    }

    @Test
    void testNegation() {
        assertEquals(-5, evaluator.evaluate("5 !"));
        assertEquals(5, evaluator.evaluate("5 ! !"));
        assertEquals(-20, evaluator.evaluate("5 4 * !"));
    }
}