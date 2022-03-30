package main.AST;

public interface Expression extends ASTNode{
    double acceptResult(ExpressionVisitor visitor);
}
