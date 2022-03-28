package main.AST;

import main.common.Position;

public interface ASTNode {
    Position getStart();

    void accept(ASTVisitor visitor);
}
