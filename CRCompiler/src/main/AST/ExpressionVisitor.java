package main.AST;

import main.AST.BinaryOperation;
import main.AST.Identifier;
import main.AST.NumValue;
import main.AST.UnaryOperation;

public interface ExpressionVisitor {
    double visitResult(UnaryOperation unaryOperation);

    double visitResult(BinaryOperation binaryOperation);

    double visitResult(NumValue numValue);

    double visitResult(Identifier identifier);
}
