package main.AST;

public interface ASTVisitor {
    void visit(Program program);

    void visit(ModelDeclaration modelDeclaration);
    void visit(FunctionDeclaration functionDeclaration);

    // Statements
    void visit(Assignment assignment);
    void visit(FunctionCall functionCall);
    void visit(PrimitiveFunctionCall primitiveFunctionCall);
    void visit(SimBlock simBlock);
    void visit(SeqBlock seqBlock);
    void visit(EmptyStatement emptyStatement);

    //Expressions
    void visit(Identifier identifier);
    void visit(BinaryOperation binaryOperation);
    void visit(UnaryOperation unaryOperation);
    void visit(NumValue numValue);
}
