package main.AST;

import main.common.Position;

public class NumValue extends AbstractASTNode implements Expression {
    private final String lexeme;

    public NumValue(Position start, String lexeme) {
        super(start);
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public double getValue() {
        return Double.parseDouble(lexeme);
    }

    @Override
    public String toString() {
        return lexeme;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double acceptResult(ExpressionVisitor visitor) {
        return visitor.visitResult(this);
    }
}
