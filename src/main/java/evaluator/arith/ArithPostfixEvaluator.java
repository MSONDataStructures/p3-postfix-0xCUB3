package evaluator.arith;

import evaluator.Evaluator;
import evaluator.IllegalPostfixExpressionException;
import language.Operand;
import language.Operator;
import parser.PostfixParser;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import stack.StackUnderflowException;


/**
 * An {@link ArithPostfixEvaluator} is a post fix evaluator
 * over simple arithmetic expressions.
 */
public class ArithPostfixEvaluator implements Evaluator<Integer> {

    private final StackInterface<Operand<Integer>> stack;

    /**
     * Constructs an {@link ArithPostfixEvaluator}.
     */
    public ArithPostfixEvaluator() {
        // Initialize to your LinkedStack
        this.stack = new LinkedStack<>();
    }

    /**
     * Evaluates a postfix expression.
     * @param expr the expression to be evaluated
     * @return the value of evaluating expr
     * @throws IllegalPostfixExpressionException if the expression is not a valid post fix expression
     */
    @Override
    public Integer evaluate(String expr) {
        // Use all the things built so far to create
        //   the algorithm for postfix evaluation
        ArithPostfixParser parser = new ArithPostfixParser(expr);
        while (parser.hasNext()) {
            switch (parser.nextType()) {
                case OPERAND:
                    // What do we do when we see an operand?
                    Operand<Integer> operand = parser.nextOperand();
                    this.stack.push(operand);
                    break;
                case OPERATOR:
                    // What do we do when we see an operator?
                    Operator<Integer> operator = parser.nextOperator();
                    int numArgs = operator.getNumberOfArguments();
                    for (int i = numArgs - 1; i >= 0; i--) {
                        try {
                            operator.setOperand(i, this.stack.pop());
                        } catch (StackUnderflowException e) {
                            throw new IllegalPostfixExpressionException("Not enough operands for operator");
                        }
                    }
                    Operand<Integer> result = operator.performOperation();
                    this.stack.push(result);
                    break;
                default:
                    // If we get here, something went very wrong
                    throw new IllegalPostfixExpressionException("Invalid expression");
            }
        }

        //What do we return?
        if (this.stack.size() != 1) {
            throw new IllegalPostfixExpressionException("Too many operands");
        }
        try {
            return this.stack.pop().getValue();
        } catch (StackUnderflowException e) {
            throw new IllegalPostfixExpressionException("Empty Stack"); // Should not happen, but handle for completeness
        }
    }
}