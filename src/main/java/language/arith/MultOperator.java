package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@link MultOperator} is an operator that performs multiplication
 * on two integers.
 * @author jcollard, jddevaug
 */
public class MultOperator extends BinaryOperator<Integer> {

    /**
     * {@inheritDoc}
     * Performs the multiplication operation.
     * @return an Operand containing the result of the multiplication
     * @throws IllegalStateException if either operand is null
     */
    @Override
    public Operand<Integer> performOperation() {
        // Follow the example from PlusOperator to override
        //   this method (from the version in BinaryOperator)
        //   for multiplication.
        Operand<Integer> op0 = this.getOp0();
        Operand<Integer> op1 = this.getOp1();
        if (op0 == null || op1 == null) {
            throw new IllegalStateException("Could not perform operation prior to operands being set.");
        }
        Integer result = op0.getValue() * op1.getValue();
        return new Operand<>(result);
    }

    @Override
    public int getPriority() {
        return 2; // Higher than plus/minus
    }
}