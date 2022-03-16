package main;

import main.AST.*;
import main.common.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CRParser implements Parser {
    private Lexer lexer;
    private ErrorLog errorLog;
    private Token current;
    private Position currentPosition;

    public CRParser(Lexer lexer, ErrorLog errorLog) {
        this.lexer = lexer;
        this.errorLog = errorLog;
        this.current = null;
        this.currentPosition = new Position(0, 0);
        skip();
    }

    private boolean willMatch(TokenType type) {
        return current != null && current.getType() == type;
    }

    Token match(TokenType type) {
        if (willMatch(type)) {
            return skip();
        } else if (current == null) {
            errorLog.add("Expected " + type + ", found end of file", currentPosition);
        } else {
            errorLog.add("Expected " + type + ", found " + current.getType(), currentPosition);
        }
        throw new ParseException("Parsing aborted");
    }

    void matchEOF() {
        if (current != null) {
            errorLog.add("Expected end of file, found " + current.getType(), currentPosition);
            throw new ParseException("Parsing aborted");
        }
    }

    Token skip() {
        Token token = current;
        if (lexer.hasNext()) {
            current = lexer.next();
            currentPosition = current.getPosition();
        } else {
            current = null;
            // keep previous value of currentPosition
        }
        return token;
    }

    @Override
    public void close() {
        lexer.close();
    }

    /*
     * Program -> ModelDecl ; FunctionDeclSequence BEGIN StatementSequence END
     */
    @Override
    public Program parseProgram() {
        Position start = currentPosition;
        ModelDeclaration modelDecl = parseModelDecl();
        List<FunctionDeclaration> funcDecls = parseFuncDeclSequence();
        match(TokenType.BEGIN);
        List<Statement> statements = parseStatementSequence();
        match(TokenType.END);
        matchEOF();
        return new Program(start, modelDecl, funcDecls, statements);
    }

    /*
     * FunctionDeclSequence -> FunctionDecl FunctionDeclSequence
     * FunctionDeclSequence ->
     */
    private List<FunctionDeclaration> parseFuncDeclSequence() {
        List<FunctionDeclaration> funcDecls = new ArrayList<>();
        while(willMatch(TokenType.FUNC)) {
            funcDecls.add(parseFuncDecl());
        }
        return funcDecls;
    }

    /*
     * FunctionDecl -> function ident ( IdentList ) { StatementSequence }
     */
    private FunctionDeclaration parseFuncDecl() {
        Position start = currentPosition;
        skip();
        Token idToken = match(TokenType.ID);
        Identifier name = new Identifier(idToken.getPosition(), idToken.getLexeme());
        match(TokenType.LPAR);
        List<Identifier> params = parseIdentList();
        match(TokenType.RPAR);
        match(TokenType.LCB);
        List<Statement> statements = parseStatementSequence();
        match(TokenType.RCB);
        return new FunctionDeclaration(start, name, params, statements);
    }

    /*
     * IdentList -> ident IdentListRest
     * IdentList ->
     */
    private List<Identifier> parseIdentList() {
        List<Identifier> identList = new ArrayList<>();
        if (willMatch(TokenType.ID)) {
            Token idToken = match(TokenType.ID);
            identList.add(new Identifier(idToken.getPosition(), idToken.getLexeme()));
        }
        identList.addAll(parseIdentListRest());
        return identList;
    }
    /*
     * IdentListRest -> , ident IdentListRest
     * IdentListRest ->
     */
    private List<Identifier> parseIdentListRest() {
        List<Identifier> identList = new ArrayList<>();
        while(willMatch(TokenType.COMMA)) {
            skip();
            Token idToken = match(TokenType.ID);
            identList.add(new Identifier(idToken.getPosition(), idToken.getLexeme()));
        }
        return identList;
    }

    /*
     * ModelDecl -> MODEL = RobotType
     * RobotType -> XBOT | YBOT
     */
    private ModelDeclaration parseModelDecl() {
        Position start = currentPosition;
        match(TokenType.MODEL);
        match(TokenType.ASSIGN);
        if(willMatch(TokenType.XBOT)) {
            skip();
            return new ModelDeclaration(start, TokenType.XBOT);
        }
        else {
            skip();
            return new ModelDeclaration(start, TokenType.YBOT);
        }
    }

    /*
     * StatementSequence -> Statement StatementSequenceRest
     * StatementSequenceRest -> ; Statement StatementSequenceRest
     * StatementSequenceRest ->
     */
    private List<Statement> parseStatementSequence() {
        return null;
    }
}
