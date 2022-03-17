package main;

import main.common.*;

import java.util.NoSuchElementException;

public class CRLexer implements Lexer {
    private Source source;
    private ErrorLog errorLog;
    private Token nextToken;

    public CRLexer(Source source, ErrorLog errorLog) {
        this.source = source;
        this.errorLog = errorLog;
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
                        nextToken = Token.create(TokenType.LCB, position);
                        return;
                    }
                    else if(c == '}') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.RCB, position);
                        return;
                    }
                    else if(c == '=') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.ASSIGN, position);
                        return;
                    }
                    else if(c == ';') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.SEMI, position);
                        return;
                    }
                    else if(c == '+') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.PLUS, position);
                        return;
                    }
                    else if(c == '-') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.MINUS, position);
                        return;
                    }
                    else if(c == '*') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.TIMES, position);
                        return;
                    }
                    else if(c == '(') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.LPAR, position);
                        return;
                    }
                    else if(c == ')') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.RPAR, position);
                        return;
                    }
                    else if(c == '.') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.PERIOD, position);
                        return;
                    }
                    else if(c == ',') {
                        position = source.getPosition();
                        source.advance();
                        nextToken = Token.create(TokenType.COMMA, position);
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
                        nextToken = Token.create(TokenType.DIVIDE, position);
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
                        nextToken = Token.createId(lexeme.toString(), position);
                        return;
                    }
                case NUMBER:
                    if(Character.isDigit(c) || c == '.') {
                        lexeme.append(c);
                        source.advance();
                        break;
                    }
                    else {
                        nextToken = Token.createNum(lexeme.toString(), position);
                        return;
                    }
                case GT:
                    if(c == '=') {
                        source.advance();
                        nextToken = Token.create(TokenType.GE, position);
                    }
                    else {
                        nextToken = Token.create(TokenType.GT, position);
                    }
                    return;
                case LT:
                    if(c == '=') {
                        source.advance();
                        nextToken = Token.create(TokenType.LE, position);
                    }
                    else {
                        nextToken = Token.create(TokenType.LT, position);
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
                nextToken = Token.createId(lexeme.toString(), position);
                return;
            case GT:
                nextToken = Token.create(TokenType.GT, position);
                return;
            case LT:
                nextToken = Token.create(TokenType.LT, position);
                return;
            case DIV:
                nextToken = Token.create(TokenType.DIVIDE, position);
                return;
            case NUMBER:
                nextToken = Token.createNum(lexeme.toString(), position);
        }
    }
}
