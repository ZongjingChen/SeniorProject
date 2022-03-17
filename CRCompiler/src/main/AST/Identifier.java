package main.AST;

import main.common.Position;

public class Identifier extends AbstractASTNode {
    private String lexeme;
    public Identifier(Position start, String lexeme) {
        super(start);
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "lexeme='" + lexeme + '\'' +
                '}';
    }
}
