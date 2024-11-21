package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@code SubOperator} is an operator that performs subtraction
 * on two integers.
 * @author jcollard, jddevaug
 */
public class SubOperator extends BinaryOperator<Integer> {
    /**
     * {@inheritDoc}
     * Performs the subtraction operation on the two operands.
     * @return an Operand containing the result of the subtraction
     * @throws IllegalStateException if the operands have not been set
     */
    @Override
    public Operand<Integer> performOperation() {
        // Follow the example from PlusOperator to override
        //   this method (from the version in BinaryOperator)
        //   for subtraction.
        Operand<Integer> op0 = this.getOp0();
        Operand<Integer> op1 = this.getOp1();
        if (op0 == null || op1 == null) {
            throw new IllegalStateException("Could not perform operation prior to operands being set.");
        }
        Integer result = op0.getValue() - op1.getValue();
        return new Operand<>(result);
    }

    @Override
    public int getPriority() {
        return 1; // Same as plus
    }
}
