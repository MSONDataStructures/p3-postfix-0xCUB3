package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@link DivOperator} is an operator that performs integer division on two
 * integers.
 * @author jcollard, jddevaug
 */
public class DivOperator extends BinaryOperator<Integer> {
    /**
     * {@inheritDoc}
     * Performs the division operation
     * @return an Operand containing the result of the division
     * @throws IllegalStateException if either operand is null
     */
    @Override
    public Operand<Integer> performOperation() {
        // Follow the example from PlusOperator to override
        // this method (from the version in BinaryOperator)
        //   for division.
        Operand<Integer> op0 = this.getOp0();
        Operand<Integer> op1 = this.getOp1();
        if (op0 == null || op1 == null) {
            throw new IllegalStateException("Could not perform operation prior to operands being set.");
        }
        Integer result = op0.getValue() / op1.getValue();
        return new Operand<>(result);
    }


    /**
     * Sets the operand at the given position. Overrides setOperand from
     * BinaryOperator to check if the denominator is zero and throw an exception
     * if it is.
     * @param i the position of the operand to set
     * @param operand the Operand to set
     * @throws IllegalStateException if the denominator is zero
     */
    @Override
    public void setOperand(int i, Operand<Integer> operand) {
        // For division, we need to additionally override
        // the setOperand method to check for division by zero.
        // See DivOperatorTest (and the README) for usage.
        if (i == 1 && operand.getValue() == 0) {
            throw new IllegalStateException("Cannot divide by zero");
        }
        super.setOperand(i, operand);
    }
}