package main.AST;

import main.common.Position;

public class NumValue extends AbstractASTNode implements Expression{
    private final String lexeme;

    public NumValue(Position start, String lexeme) {
        super(start);
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}
