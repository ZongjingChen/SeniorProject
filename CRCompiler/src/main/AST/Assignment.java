package main.AST;

import main.common.Position;

public class Assignment extends AbstractASTNode implements Statement {
    private Identifier ident;
    private Expression expression;

    public Assignment(Position start, Identifier ident, Expression expression) {
        super(start);
        this.ident = ident;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Identifier getIdent() {
        return ident;
    }

    @Override
    public String toString() {
        return ident + " = " + expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
