package main.AST;

import main.common.Position;

public class Identifier extends AbstractASTNode implements Expression{
    private String lexeme;
    public Identifier(Position start, String lexeme) {
        super(start);
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
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
