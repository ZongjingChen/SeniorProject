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

    public ModelDeclaration getModelDecl() {
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
        String result =
                "Program{\n" +
                "\t" + modelDecl;
        result += "\n\tFunctionDeclarations[";
        for(FunctionDeclaration functionDeclaration : funcDecls) {
            result = result + "\n\t\t" + functionDeclaration;
        }
        result += "\t]\n\tStatements[";
        for(Statement statement : statements) {
            result = result + "\n\t\t" + statement;
        }

        result += "\n\t]\n}";
        return result;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(Generator generator) {
        generator.generate(this);
    }
}
