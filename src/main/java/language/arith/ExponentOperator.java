package language.arith;

import language.BinaryOperator;
import language.Operand;

public class ExponentOperator extends BinaryOperator<Integer> {

    @Override
    public void setOperand(int position, Operand<Integer> operand) {
        if (position == 1 && operand.getValue() < 0) {
            throw new IllegalStateException("Negative exponents not supported for integer arithmetic");
        }
        super.setOperand(position, operand);
    }

    @Override
    public Operand<Integer> performOperation() {
        Operand<Integer> op0 = this.getOp0();
        Operand<Integer> op1 = this.getOp1();
        if (op0 == null || op1 == null) {
            throw new IllegalStateException("Could not perform operation prior to operands being set.");
        }
        return new Operand<>(pow(op0.getValue(), op1.getValue()));
    }

    private int pow(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalStateException("Negative exponents not supported for integer arithmetic");
        }
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    @Override
    public int getPriority() {
        return 3; // Highest priority
    }
}