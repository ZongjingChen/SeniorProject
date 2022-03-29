package main;

import main.AST.*;
import main.common.CheckerException;
import main.common.ErrorLog;
import main.common.TokenType;

import java.util.*;

public class CRChecker implements ASTVisitor {
    private final ErrorLog errorLog;
    // Store the function name and number of parameters
    private final HashMap<String, Integer> functionVar;
    private final List<String> environment;
    private final Stack<List<String>> stack;

    public CRChecker(ErrorLog errorLog) {
        this.errorLog = errorLog;
        this.functionVar = new HashMap<>();
        this.environment = new ArrayList<>();
        this.stack = new Stack<>();
    }

    @Override
    public void visit(Program program) {
        List<FunctionDeclaration> functionDeclarationList = program.getFuncDecls();
        for(FunctionDeclaration functionDeclaration : functionDeclarationList) {
            functionDeclaration.accept(this);
        }
        List<Statement> statements = program.getStatements();
        for(Statement statement : statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(ModelDeclaration modelDeclaration) {

    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        functionVar.put(functionDeclaration.getName().getLexeme(), functionDeclaration.getParamList().size());
        ArrayList<String> varList = new ArrayList<>();
        stack.push(varList);
        for(Identifier ident : functionDeclaration.getParamList()) {
            varList.add(ident.getLexeme());
        }
        for(Statement statement : functionDeclaration.getStatements()) {
            statement.accept(this);
        }
        stack.pop();
    }

    @Override
    public void visit(Assignment assignment) {
        List<String> current = stack.empty() ? environment : stack.peek();
        if(!current.contains(assignment.getIdent().getLexeme())) {
            current.add(assignment.getIdent().getLexeme());
        }
        assignment.getExpression().accept(this);
    }

    @Override
    public void visit(FunctionCall functionCall) {
        String name = functionCall.getIdent().getLexeme();
        if(!functionVar.containsKey(name)) {
            errorLog.add("Undeclared function [" + name + "]", functionCall.getStart());
            throw new CheckerException("Checking aborted");
        }
        else if(functionCall.getActualParams().size() != functionVar.get(name)) {
            errorLog.add("Parameter mismatch", functionCall.getStart());
            throw new CheckerException("Checking aborted");
        }
        else {
            for(Expression expression : functionCall.getActualParams()) {
                expression.accept(this);
            }
        }
    }

    @Override
    public void visit(PrimitiveFunctionCall primitiveFunctionCall) {
        TokenType body = primitiveFunctionCall.getBody();
        String name = primitiveFunctionCall.getName().getLexeme();
        // Check if this function name exists
        if(!PrimitiveFunctionCall.PrimitiveFunctionMap.containsKey(name)) {
            errorLog.add("Unknown primitive function [" + name +"]", primitiveFunctionCall.getStart());
            throw new CheckerException("Checking aborted");
        }
        // check if body has this function
        else if(!Arrays.asList(PrimitiveFunctionCall.PrimitiveFunctionMap.get(name)).contains(body)) {
            errorLog.add("Unknown primitive function [" + name +"] for [" + body.toString() + "]", primitiveFunctionCall.getStart());
            throw new CheckerException("Checking aborted");
        }
        else if((body == TokenType.BODY && primitiveFunctionCall.getParameters().size() != 1) ||
                primitiveFunctionCall.getParameters().size() != 2){
            errorLog.add("Parameter mismatch", primitiveFunctionCall.getStart());
            throw new CheckerException("Checking aborted");
        }
        else {
            for(Expression expression : primitiveFunctionCall.getParameters()) {
                expression.accept(this);
            }
        }
    }

    @Override
    public void visit(SimBlock simBlock) {
        stack.push(new ArrayList<>());
        for(Statement statement: simBlock.getStatements()) {
            statement.accept(this);
        }
        stack.pop();
    }

    @Override
    public void visit(SeqBlock seqBlock) {
        stack.push(new ArrayList<>());
        for(Statement statement: seqBlock.getStatements()) {
            statement.accept(this);
        }
        stack.pop();
    }

    @Override
    public void visit(EmptyStatement emptyStatement) {

    }

    @Override
    public void visit(Identifier identifier) {
        String name = identifier.getLexeme();
        for (List<String> strings : stack) {
            if (strings.contains(name)) {
                return;
            }
        }
        if (!environment.contains(name)) {
            errorLog.add("Unknown variable [" + name + "]", identifier.getStart());
            throw new CheckerException("Checking aborted");
        }
    }

    @Override
    public void visit(BinaryOperation binaryOperation) {
        binaryOperation.getLhs().accept(this);
        binaryOperation.getRhs().accept(this);
    }

    @Override
    public void visit(UnaryOperation unaryOperation) {
        unaryOperation.getExpression().accept(this);
    }

    @Override
    public void visit(NumValue numValue) {

    }
}
