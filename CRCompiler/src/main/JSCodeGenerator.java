package main;

import main.AST.*;
import main.common.ErrorLog;
import main.AST.ExpressionVisitor;
import main.AST.Generator;

import java.util.*;

public class JSCodeGenerator implements Generator, ExpressionVisitor {
    private final ErrorLog errorLog;
    private final HashMap<String, FunctionDeclaration> functionMap;
    private final HashMap<String, Double> environment;
    private final Deque<HashMap<String, Double>> stack;
    private double time;
    private double seqTime;
    private boolean sim;

    public JSCodeGenerator(ErrorLog errorLog) {
        this.errorLog = errorLog;
        this.functionMap = new HashMap<>();
        this.environment = new HashMap<>();
        this.stack = new ArrayDeque<>();
        this.time = 0;
        this.seqTime = -1;
        this.sim = false;
    }

    @Override
    public void generate(Program program) {
        generateCode("// Program start");
        generateCode("// ***Model Decl***");
        program.getModelDecl().accept(this);
        generateCode("// ****************\n");
        for(FunctionDeclaration functionDeclaration : program.getFuncDecls()) {
            functionDeclaration.accept(this);
        }
        generateCode("***Statements***");
        for(Statement statement : program.getStatements()) {
            statement.accept(this);
        }
        generateCode("****************\n");
        generateCode("// Program end");
    }

    @Override
    public void generate(ModelDeclaration modelDeclaration) {
        generateCode("const MODEL_PATH = " + modelDeclaration.getModel().toString() +"_MODEL_PATH;");
    }

    @Override
    public void generate(FunctionDeclaration functionDeclaration) {
        functionMap.put(functionDeclaration.getName().getLexeme(), functionDeclaration);
    }

    @Override
    public void generate(Assignment assignment) {
        HashMap<String, Double> current = stack.isEmpty()? environment : stack.peek();
        current.put(assignment.getIdent().getLexeme(), assignment.getExpression().acceptResult(this));
    }

    @Override
    public void generate(FunctionCall functionCall) {
        HashMap<String, Double> current = new HashMap<>();
        List<Identifier> paramList = functionMap.get(functionCall.getIdent().getLexeme()).getParamList();
        List<Expression> actualParamList = functionCall.getActualParams();
        for(int i = 0; i < actualParamList.size(); i++) {
            current.put(paramList.get(i).getLexeme(), actualParamList.get(i).acceptResult(this));
        }
        stack.push(current);
        for(Statement statement : functionMap.get(functionCall.getIdent().getLexeme()).getStatements()) {
            statement.accept(this);
        }
        stack.pop();
    }

    @Override
    public void generate(PrimitiveFunctionCall primitiveFunctionCall) {

        String body = primitiveFunctionCall.getBody().toString();
        String function = primitiveFunctionCall.getName().getLexeme();

        // Example: HEAD.rotate(degree, duration);
        double degree = primitiveFunctionCall.getDegree(this);
        double duration = primitiveFunctionCall.getDuration(this);

        if(seqTime != -1) {
            generateCode(function + body + "(" + degree + "," + seqTime + "," + duration + ");");
            seqTime += duration;
        }
        else {
            generateCode(function + body + "(" + degree + "," + time + "," + duration + ");");
            if(!sim) {
                time += duration;
            }
        }
    }

    private void generate(PrimitiveFunctionCall primitiveFunctionCall, double time) {

    }

    @Override
    public void generate(SimBlock simBlock) {
        if seqBlock{
            update seqTime
        }
        else if simBlock{

        }
        else{
            find logest duration;
            update global time;
        }
    }

    @Override
    public void generate(SeqBlock seqBlock) {
//        if(seqBlock) {
//            update seqTime
//        }
//        else if(SimBlock) {
//
//        }
//        else {
//            generate(seqBlock.getStatements());
//        }
    }

    @Override
    public void generate(EmptyStatement emptyStatement) {

    }

    @Override
    public double visitResult(UnaryOperation unaryOperation) {
        double value = unaryOperation.getExpression().acceptResult(this);
        if(unaryOperation.getOperator() == UnaryOperation.OpType.MINUS) {
            value = - value;
        }
        return value;
    }

    @Override
    public double visitResult(BinaryOperation binaryOperation) {
        double lhs = binaryOperation.getLhs().acceptResult(this);
        double rhs = binaryOperation.getRhs().acceptResult(this);
        switch (binaryOperation.getOperation()) {
            case DIV:
                return lhs / rhs;
            case MOD:
                return lhs % rhs;
            case PLUS:
                return lhs + rhs;
            case MINUS:
                return lhs - rhs;
            case TIMES:
                return lhs * rhs;
            default:
                return 0;
        }
    }

    @Override
    public double visitResult(NumValue numValue) {
        return Double.parseDouble(numValue.getLexeme());
    }

    @Override
    public double visitResult(Identifier identifier) {
        stack.iterator();
        for(HashMap<String, Double> map : stack) {
            if (map.containsKey(identifier.getLexeme())) {
                return map.get(identifier.getLexeme());
            }
        }
        return environment.get(identifier.getLexeme());
    }

    public void generateCode(String msg) {
        System.out.println(msg);
    }
}
