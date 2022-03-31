package main.AST;

import main.common.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimBlock extends AbstractASTNode implements Statement, TimeConsumable{
    private final List<Statement> statements;
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

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(Generator generator) {
        generator.generate(this);
    }

    public double getDuration(ExpressionVisitor visitor) {
        double longestDuration = 0;
        for(Statement statement : statements) {
            if(statement instanceof TimeConsumable) {
                longestDuration = Math.max(longestDuration, ((TimeConsumable) statement).getDuration(visitor));
            }
        }

        return longestDuration;
    }

}
