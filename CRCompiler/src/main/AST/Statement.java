package main.AST;

public interface Statement extends ASTNode{
    void accept(Generator generator);
}
