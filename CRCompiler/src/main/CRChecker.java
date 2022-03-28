package main;

import main.AST.*;
import main.common.ErrorLog;

public class CRChecker implements ASTVisitor {
    private ErrorLog errorLog;

    public CRChecker(ErrorLog errorLog) {
        this.errorLog = errorLog;
    }

    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(ModelDeclaration modelDeclaration) {

    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {

    }

    @Override
    public void visit(Assignment assignment) {

    }

    @Override
    public void visit(FunctionCall functionCall) {

    }

    @Override
    public void visit(PrimitiveFunctionCall primitiveFunctionCall) {

    }

    @Override
    public void visit(SimBlock simBlock) {

    }

    @Override
    public void visit(SeqBlock seqBlock) {

    }

    @Override
    public void visit(EmptyStatement emptyStatement) {

    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(BinaryOperation binaryOperation) {

    }

    @Override
    public void visit(UnaryOperation unaryOperation) {

    }

    @Override
    public void visit(NumValue numValue) {

    }
}
