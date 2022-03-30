package main.AST;

import main.common.Position;

import java.util.ArrayList;
import java.util.List;

public class SeqBlock extends Block {

    public SeqBlock(Position start, List<Statement> statements) {
        super(start, statements);
    }

    @Override
    public String toString() {
        return "SeqBlock{" +
                "statements=" + getStatements() +
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
}
