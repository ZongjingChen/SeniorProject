package main.AST;

import main.common.Position;

import java.util.List;

public class SimBlock extends AbstractASTNode implements Statement{
    private List<Statement> statements;

    public SimBlock(Position start, List<Statement> statements) {
        super(start);
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "SimBlock{" +
                "statements=" + statements +
                '}';
    }
}
