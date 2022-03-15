package main.DecompiledDeCLanModel;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import main.common.Position;
import main.common.Token;
import main.common.TokenType;

public class TokenImpl implements Token {
    private final TokenType a;
    private final String b;
    private final Position c;

    TokenImpl(Position position, TokenType type, String lexeme) {
        this.c = position;
        this.a = type;
        this.b = lexeme;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder(this.a.toString());
        if (this.b != null) {
            var1.append(" ").append(this.b);
        }

        var1.append(" ").append(this.c);
        return var1.toString();
    }

    public TokenType getType() {
        return this.a;
    }

    public String getLexeme() {
        return this.b;
    }

    public Position getPosition() {
        return this.c;
    }
}
