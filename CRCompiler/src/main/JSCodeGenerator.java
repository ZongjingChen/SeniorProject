package main;

import main.AST.*;
import main.AST.Timer;
import main.common.ErrorLog;
import main.AST.ExpressionVisitor;
import main.AST.Generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JSCodeGenerator implements Generator, ExpressionVisitor {
    private final ErrorLog errorLog;
    private final HashMap<String, FunctionDeclaration> functionMap;
    private final HashMap<String, Double> environment;
    private final Deque<HashMap<String, Double>> stack;
    private final Timer timer;
    private final FileWriter writer;

    public JSCodeGenerator(ErrorLog errorLog, String path) {
        this.errorLog = errorLog;
        this.functionMap = new HashMap<>();
        this.environment = new HashMap<>();
        this.stack = new ArrayDeque<>();
        this.timer = new Timer();

        // Create a file object
        File output = new File(path);
        FileWriter writer = null;
        try {
            output.createNewFile();
            writer = new FileWriter(path, true);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.writer = writer;
    }

    @Override
    public void generate(Program program) {
        generateCode("MODELDECL");
        program.getModelDecl().accept(this);
        generateCode("END\n");
        for(FunctionDeclaration functionDeclaration : program.getFuncDecls()) {
            functionDeclaration.accept(this);
        }
        generateCode("STATEMENTS");
        for(Statement statement : program.getStatements()) {
            statement.accept(this);
        }
        generateCode("END");

        try {
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

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

    /**
     * Function call is handled as a seqBlock
     * @param functionCall
     */
    @Override
    public void generate(FunctionCall functionCall) {
        // Push a new variable map
        HashMap<String, Double> current = new HashMap<>();
        List<Identifier> paramList = functionMap.get(functionCall.getIdent().getLexeme()).getParamList();
        List<Expression> actualParamList = functionCall.getActualParams();
        for(int i = 0; i < actualParamList.size(); i++) {
            current.put(paramList.get(i).getLexeme(), actualParamList.get(i).acceptResult(this));
        }
        stack.push(current);

        // Only add new SEQ scope if this function call is in a sim block
        if(timer.getCurrentScope() == Timer.Scope.SIM) {
            timer.addNewTime(Timer.Scope.SEQ, timer.getCurrentTime());
        }

        for(Statement statement : functionMap.get(functionCall.getIdent().getLexeme()).getStatements()) {
            statement.accept(this);
        }

        // Pop the SEQ scope
        if(timer.getCurrentScope() == Timer.Scope.SIM) {
            timer.pop();
        }

        stack.pop();
    }

    /**
     * Only update currentTime when in a seqBlock or not in a block. SimBlock will update time for statements.
     * @param primitiveFunctionCall
     */
    @Override
    public void generate(PrimitiveFunctionCall primitiveFunctionCall) {
        String body = primitiveFunctionCall.getBody().toString();
        String function = primitiveFunctionCall.getName().getLexeme();

        // Example: HEAD.rotate(degree, duration);
        double degree = primitiveFunctionCall.getDegree(this);
        double duration = primitiveFunctionCall.getDuration(this);

        generateCode(function + body + "(" + degree + "," + timer.getCurrentTime() + "," + duration + ");");

        // If not under SIM scope, update current time
        if(timer.getCurrentScope() != Timer.Scope.SIM) {
            timer.updateTime(duration);
        }
    }

    /**
     * generate a simBlock will update the time for all its statements.
     * @param simBlock
     */
    @Override
    public void generate(SimBlock simBlock) {
        // Add new variable map
        HashMap<String, Double> currentMap = new HashMap<>();
        stack.push(currentMap);

        // Add new time scope
        double newTime = timer.getCurrentTime();
        timer.addNewTime(Timer.Scope.SIM, newTime);

        for(Statement statement : simBlock.getStatements()) {
            statement.accept(this);
        }

        timer.pop();

        // Update time
        timer.updateTime(simBlock.getDuration(this));

        stack.pop();
    }

    /**
     * A seqBlock uses its local time. If the outer scope is SEQ, there is no need to add a new SEQ scope.
     * @param seqBlock
     */
    @Override
    public void generate(SeqBlock seqBlock) {
        // Add new variable map
        HashMap<String, Double> currentMap = new HashMap<>();
        stack.push(currentMap);

        // Only add new SEQ scope if this seq block is in a sim block
        if(timer.getCurrentScope() == Timer.Scope.SIM) {
            timer.addNewTime(Timer.Scope.SEQ, timer.getCurrentTime());
        }

        for(Statement statement : seqBlock.getStatements()) {
            statement.accept(this);
        }

        // Pop the SEQ block
        if(timer.getCurrentScope() == Timer.Scope.SIM) {
            timer.pop();
        }

        stack.pop();
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
        try {
            writer.write(msg);
            writer.write("\n");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // For test
//        System.out.println(msg);
    }
}
