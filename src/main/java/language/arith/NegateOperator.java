package language.arith;

import language.Operand;

/**
 * The {@link NegateOperator} is an operator that performs negation
 * on a single integer.
 * @author jcollard, jddevaug
 */
public class NegateOperator extends UnaryOperator<Integer> {

    /**
     * {@inheritDoc}
     * Performs the negation operation on the operand.
     * @return an Operand containing the result of the negation
     * @throws IllegalStateException if the operand has not been set
     */
    @Override
    public Operand<Integer> performOperation() {
        // Follow the example from PlusOperator to override
        //   this method (from the version in UnaryOperator)
        //   for negation.
        Operand<Integer> op0 = this.getOp0();
        if (op0 == null) {
            throw new IllegalStateException("Could not perform operation prior to operands being set.");
        }
        Integer result = -op0.getValue();
        return new Operand<Integer>(result);
    }
}