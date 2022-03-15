package main.DecompiledDeCLanModel;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import main.common.Position;
import main.common.Token;
import main.common.TokenFactory;
import main.common.TokenType;

public class TokenFactoryImpl implements TokenFactory {
    public TokenFactoryImpl() {
    }

    public Token makeToken(TokenType type, Position position) {
        return new TokenImpl(position, type, (String)null);
    }

    public Token makeIdToken(String lexeme, Position position) {
        return TokenType.reserved.containsKey(lexeme) ? new TokenImpl(position, (TokenType)TokenType.reserved.get(lexeme), (String)null) : new TokenImpl(position, TokenType.ID, lexeme);
    }

    public Token makeNumToken(String lexeme, Position position) {
        return new TokenImpl(position, TokenType.NUM, lexeme);
    }
}
