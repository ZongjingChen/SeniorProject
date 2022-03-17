package main.AST;

import main.common.Position;
import main.common.TokenType;

import java.util.List;

public class PrimitiveFunctionCall extends AbstractASTNode implements Statement {
    private final TokenType body;
    private final Identifier name;
    private final List<Expression> parameters;

    public PrimitiveFunctionCall(Position start, TokenType body, Identifier name, List<Expression> parameters) {
        super(start);
        this.body = body;
        this.name = name;
        this.parameters = parameters;
    }

    public List<Expression> getParameters() {
        return parameters;
    }

    public Identifier getName() {
        return name;
    }

    public TokenType getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "PrimitiveFunctionCall{" +
                "body=" + body +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
