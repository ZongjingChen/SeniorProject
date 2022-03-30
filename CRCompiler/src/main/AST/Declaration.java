package main.AST;

public interface Declaration extends ASTNode{
    void accept(Generator generator);
}

