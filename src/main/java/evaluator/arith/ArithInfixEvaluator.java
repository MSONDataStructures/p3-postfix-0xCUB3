package evaluator.arith;

import evaluator.Evaluator;
import evaluator.IllegalPostfixExpressionException;
import language.BinaryOperator;
import language.Operand;
import language.Operator;
import language.arith.*;
import stack.LinkedStack;
import stack.StackInterface;

import java.util.HashMap;
import java.util.Map;

public class ArithInfixEvaluator implements Evaluator<Integer> {
    private final StackInterface<Operand<Integer>> operandStack;
    private final StackInterface<Operator<Integer>> operatorStack;
    private final Map<String, Operator<Integer>> operators;

    private static class LeftParenthesis extends BinaryOperator<Integer> {
        @Override
        public Operand<Integer> performOperation() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getPriority() {
            return -1;
        }
    }

    public ArithInfixEvaluator() {
        operandStack = new LinkedStack<>();
        operatorStack = new LinkedStack<>();
        operators = new HashMap<>();
        operators.put("+", new PlusOperator());
        operators.put("-", new SubOperator());
        operators.put("*", new MultOperator());
        operators.put("/", new DivOperator());
        operators.put("^", new ExponentOperator());
        operators.put("!", new NegateOperator()); // not sure if we wanted this but I added it
    }

    @Override
    public Integer evaluate(String expr) {
        if (expr == null || expr.trim().isEmpty()) {
            throw new IllegalPostfixExpressionException("Expression cannot be null or empty");
        }

        clearStacks();
        StringBuilder currentToken = new StringBuilder();
        char[] chars = expr.toCharArray();

        for (char c : chars) {
            if (Character.isWhitespace(c)) {
                processToken(currentToken.toString());
                currentToken.setLength(0);
                continue;
            }

            if (c == '(' || c == ')' || isOperator(String.valueOf(c))) {
                if (!currentToken.isEmpty()) {
                    processToken(currentToken.toString());
                    currentToken.setLength(0);
                }
                processToken(String.valueOf(c));
            } else {
                currentToken.append(c);
            }
        }

        if (!currentToken.isEmpty()) {
            processToken(currentToken.toString());
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.top() instanceof LeftParenthesis) {
                throw new IllegalPostfixExpressionException("Mismatched parentheses");
            }
            applyOperator();
        }

        if (operandStack.isEmpty()) {
            throw new IllegalPostfixExpressionException("Invalid expression");
        }

        Integer result = operandStack.pop().getValue();
        if (!operandStack.isEmpty()) {
            throw new IllegalPostfixExpressionException("Invalid expression");
        }

        return result;
    }

    private void processToken(String token) {
        if (token.isEmpty()) {
            return;
        }

        if (isNumber(token)) {
            operandStack.push(new Operand<>(Integer.parseInt(token)));
        } else if (token.equals("(")) {
            operatorStack.push(new LeftParenthesis());
        } else if (token.equals(")")) {
            processUntilLeftParen();
        } else if (token.equals("!")) {
            if (operandStack.isEmpty()) {
                throw new IllegalPostfixExpressionException("No operand for negation operator");
            }
            Operator<Integer> op = new NegateOperator();
            op.setOperand(0, operandStack.pop());
            operandStack.push(op.performOperation());
        } else if (isOperator(token)) {
            Operator<Integer> currentOp = getNewOperator(token);
            while (!operatorStack.isEmpty() &&
                    !(operatorStack.top() instanceof LeftParenthesis) &&
                    (currentOp.getPriority() < operatorStack.top().getPriority() ||
                            (currentOp.getPriority() == operatorStack.top().getPriority() && !token.equals("^")))) {
                applyOperator();
            }
            operatorStack.push(currentOp);
        } else {
            throw new IllegalPostfixExpressionException("Invalid token: " + token);
        }
    }

    private boolean isOperator(String token) {
        return operators.containsKey(token);
    }

    private void clearStacks() {
        while (!operandStack.isEmpty()) {
            operandStack.pop();
        }
        while (!operatorStack.isEmpty()) {
            operatorStack.pop();
        }
    }

    private Operator<Integer> getNewOperator(String token) {
        return switch (token) {
            case "+" -> new PlusOperator();
            case "-" -> new SubOperator();
            case "*" -> new MultOperator();
            case "/" -> new DivOperator();
            case "^" -> new ExponentOperator();
            case "!" -> new NegateOperator();
            default -> throw new IllegalPostfixExpressionException("Unknown operator: " + token);
        };
    }

    private void processUntilLeftParen() {
        boolean foundLeftParen = false;
        while (!operatorStack.isEmpty()) {
            if (operatorStack.top() instanceof LeftParenthesis) {
                operatorStack.pop();
                foundLeftParen = true;
                break;
            }
            applyOperator();
        }
        if (!foundLeftParen) {
            throw new IllegalPostfixExpressionException("Mismatched parentheses");
        }
    }

    private void applyOperator() {
        if (operatorStack.isEmpty()) {
            throw new IllegalPostfixExpressionException("Invalid expression");
        }

        Operator<Integer> op = operatorStack.pop();
        if (op instanceof LeftParenthesis) {
            throw new IllegalPostfixExpressionException("Invalid expression");
        }

        try {
            int numArgs = op.getNumberOfArguments();
            if (operandStack.size() < numArgs) {
                throw new IllegalPostfixExpressionException("Not enough operands");
            }

            if (numArgs == 2) {
                Operand<Integer> b = operandStack.pop();
                Operand<Integer> a = operandStack.pop();
                op.setOperand(0, a);
                op.setOperand(1, b);
            } else {
                op.setOperand(0, operandStack.pop());
            }

            operandStack.push(op.performOperation());
        } catch (IllegalStateException e) {
            throw new IllegalPostfixExpressionException("Invalid expression: " + e.getMessage());
        }
    }

    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}