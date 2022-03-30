package main.AST;

import main.common.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Block extends AbstractASTNode implements Statement{
    private final List<Statement> statements;

    public Block(Position start, List<Statement> statements) {
        super(start);
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<Expression> getDurations() {
        List<Expression> expressions = new ArrayList<>();
        for(Statement statement : statements) {
            if(statement instanceof PrimitiveFunctionCall) {
                expressions.add(((PrimitiveFunctionCall) statement).getParameters().get(1));
            }
            else if(statement instanceof SimBlock) {
                expressions.addAll(((SimBlock) statement).getDurations());
            }
            else if(statement instanceof SeqBlock) {
                expressions.addAll(((SeqBlock) statement).getDurations());
            }
        }

        return expressions;
    }

}

