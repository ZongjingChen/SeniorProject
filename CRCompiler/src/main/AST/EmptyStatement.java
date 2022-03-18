package main.AST;

import main.common.Position;

public class EmptyStatement extends AbstractASTNode implements Statement {

    public EmptyStatement(Position start) {
        super(start);
    }

    @Override
    public String toString() {
        return "";
    }
}
