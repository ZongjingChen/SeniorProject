package main.AST;

import main.common.Position;

import java.util.List;

public class Program extends AbstractASTNode {
    private ModelDeclaration modelDecl;
    private final List<FunctionDeclaration> funcDecls;
    private final List<Statement> statements;

    public Program(Position start, ModelDeclaration modelDecl, List<FunctionDeclaration> funcDecls, List<Statement> statements) {
        super(start);
        this.modelDecl = modelDecl;
        this.funcDecls = funcDecls;
        this.statements = statements;
    }

    public ModelDeclaration getModel() {
        return modelDecl;
    }

    public List<FunctionDeclaration> getFuncDecls(){
        return funcDecls;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "Program{\n" +
                "\tmodelDecl=" + modelDecl +
                "\tfuncDecls=" + funcDecls +
                "\tstatements=" + statements +
                "\n}";
    }
}
