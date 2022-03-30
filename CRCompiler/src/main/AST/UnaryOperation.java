package main.AST;

import main.common.Position;

public class UnaryOperation extends AbstractASTNode implements Expression{
    private final OpType operator;
    private final Expression expression;

    public UnaryOperation(Position start, OpType operator, Expression expression) {
        super(start);
        this.operator = operator;
        this.expression = expression;
    }

    public OpType getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double acceptResult(ExpressionVisitor visitor) {
        return visitor.visitResult(this);
    }

    public enum OpType {
        PLUS("+"),
        MINUS("-");

        private String string;

        OpType(String s) {
            this.string = s;
        }

        @Override
        public String toString() {
            return string;
        }
    }

//    @Override
//    public String toString() {
//        return "UnaryOperation{" +
//                "operator=" + operator +
//                ", expression=" + expression +
//                '}';
//    }

    @Override
    public String toString() {
        return operator + " " + "(" + expression + ")";
    }
}
