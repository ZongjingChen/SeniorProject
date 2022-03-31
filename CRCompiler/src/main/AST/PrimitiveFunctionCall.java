package main.AST;

import main.common.Position;
import main.common.TokenType;

import java.util.List;
import java.util.Map;

public class PrimitiveFunctionCall extends AbstractASTNode implements Statement, TimeConsumable {
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

    public static final Map<String, TokenType[]> PrimitiveFunctionMap = Map.of(
            "raise", new TokenType[]{TokenType.LArm, TokenType.RArm, TokenType.LForeArm, TokenType.RForeArm,
                                        TokenType.LLeg, TokenType.RLeg, TokenType.LUpLeg, TokenType.RUpLeg, TokenType.HEAD},
            "lateralRaise", new TokenType[]{TokenType.LArm, TokenType.RArm, TokenType.LForeArm, TokenType.RForeArm,
                                                TokenType.LLeg, TokenType.RLeg, TokenType.LUpLeg, TokenType.RUpLeg},
            "rotate", new TokenType[]{TokenType.HEAD, TokenType.BODY, TokenType.LHand, TokenType.RHand, TokenType.LFoot, TokenType.RFoot},
            "tilt", new TokenType[]{TokenType.HEAD},
            "dance", new TokenType[]{TokenType.BODY}
    );

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(Generator generator) {
        generator.generate(this);
    }

    public double getDuration(ExpressionVisitor visitor) {
        return parameters.get(1).acceptResult(visitor);
    }

    public double getDegree(ExpressionVisitor visitor) {
        return parameters.get(0).acceptResult(visitor);
    }
}
