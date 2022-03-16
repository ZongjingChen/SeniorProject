package main.AST;

import main.common.Position;
import main.common.TokenType;

public class ModelDeclaration extends AbstractASTNode{
    private TokenType model;

    public ModelDeclaration (Position start, TokenType model) {
        super(start);
        this.model = model;
    }

    public TokenType getModel() {
        return model;
    }

}
