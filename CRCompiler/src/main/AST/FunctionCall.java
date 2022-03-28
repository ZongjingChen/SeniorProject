package main.AST;

import main.common.Position;

import java.util.List;

public class FunctionCall extends AbstractASTNode implements Statement{
    private Identifier name;
    private List<Expression> actualParams;

    public FunctionCall(Position start, Identifier name, List<Expression> actualParams) {
        super(start);
        this.name = name;
        this.actualParams = actualParams;
    }

    public Identifier getName() {
        return name;
    }

    public List<Expression> getActualParams() {
        return actualParams;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
