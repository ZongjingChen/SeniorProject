package main;

import main.common.*;

import java.util.NoSuchElementException;

public class CRLexer implements Lexer {
    private Source source;
    private TokenFactory tokenFactory;
    private Token nextToken;

    public CRLexer(Source source, TokenFactory tokenFactory) {
        this.source = source;
        this.tokenFactory = tokenFactory;
        this.nextToken = null;
    }

    @Override
    public void close() {
        source.close();
    }

    @Override
    public boolean hasNext() {
        if(nextToken == null) {
            scanNext();
        }

        return nextToken != null;
    }

    @Override
    public Token next() {
        if(nextToken == null) {
            scanNext();
        }

        if(nextToken == null) {
            throw new NoSuchElementException("No more tokens");
        }

        Token result = nextToken;
        nextToken = null;
        return result;
    }

    private static enum State {
        INIT, IDENT, NUMBER, DIV, GT, LT, COMMENT
    }
    private void scanNext() {
        State state = State.INIT;
        StringBuilder lexeme = new StringBuilder();
        Position position = null;

        while(!source.atEOF()) {
            char c = source.current();
            switch (state){
                case INIT:
                    if(Character.isWhitespace(c)) {
                        source.advance();
                        break;
                    }
                    else if(Character.isLetter(c)) {
                        state = State.IDENT;
                        lexeme.append(c);
                        position = source.getPosition();
                        source.advance();
                        break;
                    }
                    else if(Character.isDigit(c)) {
                        state = State.NUMBER;
                        lexeme.append(c);
                        position = source.getPosition();
                        source.advance();
                        break;
                    }
                    else if(c == '>') {
                        state = State.GT;
                        position = source.getPosition();
                        source.advance();
                        break;
                    }
                    else if(c == '<') {
                        state = State.LT;
                        position = source.getPosition();
                        source.advance();
                        break;
                    }
                    else if(c == '/') {
                        state = State.DIV;
                        position = source.getPosition();
                        source.advance();
                        break;
                    }
                    else if(c == '{') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.LCB, position);
                        return;
                    }
                    else if(c == '}') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.RCB, position);
                        return;
                    }
                    else if(c == '=') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.ASSIGN, position);
                        return;
                    }
                    else if(c == ';') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.SEMI, position);
                        return;
                    }
                    else if(c == '+') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.PLUS, position);
                        return;
                    }
                    else if(c == '-') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.MINUS, position);
                        return;
                    }
                    else if(c == '*') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.TIMES, position);
                        return;
                    }
                    else if(c == '(') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.LPAR, position);
                        return;
                    }
                    else if(c == ')') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.RPAR, position);
                        return;
                    }
                    else if(c == '.') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.PERIOD, position);
                        return;
                    }
                    else if(c == ',') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.COMMA, position);
                        return;
                    }
                    else {
                        position = source.getPosition();
                        System.err.println("Unrecognized character '" + c + "' at " + position);
                        source.advance();
                        break;
                    }
                case DIV:
                    if(c == '/') {
                        state = State.COMMENT;
                        source.advance();
                        break;
                    }
                    else {
                        nextToken = tokenFactory.makeToken(TokenType.DIVIDE, position);
                        return;
                    }
                case COMMENT:
                    if (source.getPosition().getColumn() != 1) {
                        source.advance();
                    }
                    else {
                        state = State.INIT;
                    }
                    break;
                case IDENT:
                    if(Character.isLetterOrDigit(c)) {
                        lexeme.append(c);
                        source.advance();
                        break;
                    }
                    else {
                        nextToken = tokenFactory.makeIdToken(lexeme.toString(), position);
                        return;
                    }
                case NUMBER:
                    if(Character.isDigit(c) || c == '.') {
                        lexeme.append(c);
                        source.advance();
                        break;
                    }
                    else {
                        nextToken = tokenFactory.makeNumToken(lexeme.toString(), position);
                        return;
                    }
                case GT:
                    if(c == '=') {
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.GE, position);
                    }
                    else {
                        nextToken = tokenFactory.makeToken(TokenType.GT, position);
                    }
                    return;
                case LT:
                    if(c == '=') {
                        source.advance();
                        nextToken = tokenFactory.makeToken(TokenType.LE, position);
                    }
                    else {
                        nextToken = tokenFactory.makeToken(TokenType.LT, position);
                    }
                    return;
            }
        }

        // clean up at the end of source
        switch (state) {
            case INIT:
            case COMMENT:
                nextToken = null;
                return;
            case IDENT:
                nextToken = tokenFactory.makeIdToken(lexeme.toString(), position);
                return;
            case GT:
                nextToken = tokenFactory.makeToken(TokenType.GT, position);
                return;
            case LT:
                nextToken = tokenFactory.makeToken(TokenType.LT, position);
                return;
            case DIV:
                nextToken = tokenFactory.makeToken(TokenType.DIVIDE, position);
                return;
            case NUMBER:
                nextToken = tokenFactory.makeNumToken(lexeme.toString(), position);
        }
    }
}
