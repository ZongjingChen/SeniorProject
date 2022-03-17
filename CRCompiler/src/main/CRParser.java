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
        match(TokenType.SEMI);
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
        while(willMatch(TokenType.function)) {
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
     */
    private List<Statement> parseStatementSequence() {
        List<Statement> statements = new ArrayList<>();
        statements.add(parseStatement());
        statements.addAll(parseStatementRest());
        return statements;
    }

    /*
     * StatementSequenceRest -> ; Statement StatementSequenceRest
     * StatementSequenceRest ->
     */
    private List<Statement> parseStatementRest() {
        List<Statement> statements = new ArrayList<>();
        while(willMatch(TokenType.SEMI)) {
            skip();
            statements.add(parseStatement());
        }
        return statements;
    }

    /*
    Statement -> Assignment | FunctionCall | PrimitiveFunctionCall | SimBlock | SeqBlock
     */
    private Statement parseStatement() {
        if (willMatch(TokenType.ID)) {
            Token token = skip();
            if (willMatch(TokenType.ASSIGN)) {
                return parseAssignment();
            }
            else {
//                return parseFunctionCall();
                return null;
            }
        }
        else if (willMatch(TokenType.sim)) {
            return parseSimBlock();
        }
        else if (willMatch(TokenType.seq)) {
            return parseSeqBlock();
        }
        else {
            for (TokenType type : TokenType.bodyParts) {
                if (willMatch(type)) {
                    return parsePrimitiveFunctionCall();
                }
            }
        }
        return new EmptyStatement(currentPosition);
    }

    /*
     * Assignment -> ident = Expression
     */
    private Statement parseAssignment() {
        Position start = currentPosition;
        Token idToken = skip();
        Identifier ident = new Identifier(idToken.getPosition(), idToken.getLexeme());
        match(TokenType.ASSIGN);
        Expression expression = parseExpression();
        return new Assignment(start, ident, expression);
    }

    /*
     * SimBlock -> sim { StatementSequence }
     */
    private Statement parseSimBlock() {
        Position start = currentPosition;
        skip();
        match(TokenType.LCB);
        List<Statement> statements = parseStatementSequence();
        match(TokenType.RCB);
        return new SimBlock(start, statements);
    }

    /*
     * SeqBlock -> sim { StatementSequence }
     */
    private Statement parseSeqBlock() {
        Position start = currentPosition;
        skip();
        match(TokenType.LCB);
        List<Statement> statements = parseStatementSequence();
        match(TokenType.RCB);
        return new SeqBlock(start, statements);
    }

    /*
     * PrimitiveFunctionCall -> BodyPart . BuiltInFunctionName ActualParameters
     */
    private Statement parsePrimitiveFunctionCall() {
        Position start = currentPosition;
        Token body = skip();
        match(TokenType.PERIOD);
        Token functionName = skip();
        List<Expression> identList = parseActualParams();
        return new PrimitiveFunctionCall(start, body.getType(), new Identifier(functionName.getPosition(), functionName.getLexeme()), identList);
    }

    /*
     * ActualParameters -> ( ExpList )
     * ActualParameters -> ( )
     */
    private List<Expression> parseActualParams() {
        match(TokenType.LPAR);
        List<Expression> expressionList = new ArrayList<>();
        if(!willMatch(TokenType.RPAR)) {
            expressionList = parseExpList();
        }
        match(TokenType.RPAR);
        return expressionList;
    }

    /*
     * ExpList -> Expression ExpListRest
     */
    private List<Expression> parseExpList() {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(parseExpression());
        expressionList.addAll(parseExpListRest());
        return expressionList;
    }

    /*
     * ExpListRest -> , Expression
     * ExpListRest
     */
    private List<Expression> parseExpListRest() {
        List<Expression> expressionList = new ArrayList<>();
        while(willMatch(TokenType.COMMA)) {
            skip();
            expressionList.add(parseExpression());
        }
        return expressionList;
    }

    /*
     * Expression -> + Term ExprRest
     * Expression -> - Term ExprRest
     * Expression -> Term ExprRest
     */
    private Expression parseExpression() {
        //TODO:
        return null;
    }
}
