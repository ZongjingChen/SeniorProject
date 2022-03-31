package main.AST;

import main.common.Position;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SeqBlock extends AbstractASTNode implements Statement, TimeConsumable{
    private final List<Statement> statements;
    public SeqBlock(Position start, List<Statement> statements) {
        super(start);
        this.statements = statements;
    }
    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "SeqBlock{" +
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
        double sumDuration = 0;
        for(Statement statement : statements) {
            if(statement instanceof TimeConsumable) {
                sumDuration += ((TimeConsumable) statement).getDuration(visitor);
            }
        }

        return sumDuration;
    }
}
