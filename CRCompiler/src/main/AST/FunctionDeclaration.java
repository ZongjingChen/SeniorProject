package main.AST;

import main.common.Position;

import java.util.List;

public class FunctionDeclaration extends AbstractASTNode{
    private Identifier name;
    private List<Identifier> paramList;
    private List<Statement> statements;

    public FunctionDeclaration(Position start, Identifier ident, List<Identifier> identList, List<Statement> statements) {
        super(start);
        this.name = ident;
        this.paramList = identList;
        this.statements = statements;
    }

    public Identifier getName() {
        return name;
    }

    public List<Identifier> getParamList() {
        return paramList;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "FunctionDeclaration{\n" +
                "\tname=" + name +
                "\tparamList=" + paramList +
                "\tstatements=" + statements +
                "\n";
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
