package language.arith;

import language.Operand;
import language.Operator;

/**
 * Abstract class for unary operators.
 * @param <T> the type of the operand
 */
public abstract class UnaryOperator<T> implements Operator<T> {

    private Operand<T> op0;

    /**
     * {@inheritDoc}
     * Returns the number of arguments required by the unary operator (1).
     * @return the number of arguments
     */
    @Override
    public int getNumberOfArguments() {
        return 1; // this one is on the house
    }

    /**
     * {@inheritDoc}
     * Sets the operand for the unary operator.
     * @param i the position of the operand (must be 0)
     * @param operand the operand to be set
     * @throws NullPointerException if the operand is null
     * @throws IllegalArgumentException if the position i is not 0
     * @throws IllegalStateException if the operand at position i has already been set
     */
    @Override
    public void setOperand(int i, Operand<T> operand) {
        // Modify the example from the BinaryOperator
        //   implementation to implement this UnaryOperator
        //   abstract class (which will have NegateOperator
        //   as a subclass).
        if (operand == null) {
            throw new NullPointerException("Could not set null operand.");
        }
        if (i > 0 || i < 0) {
            throw new IllegalArgumentException("Unary operator only accepts operand 0 but received " + i + ".");
        }
        if (this.op0 != null) {
            throw new IllegalStateException("Position " + i + " has been previously set.");
        }
        this.op0 = operand;
    }

    /**
     * Returns the first operand.
     * @return the first operand
     */
    public Operand<T> getOp0() {
        return this.op0; // this one is better than ever
    }
}