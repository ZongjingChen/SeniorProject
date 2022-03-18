package main.AST;

import main.common.Position;

public class BinaryOperation extends AbstractASTNode implements Expression{
    private Expression lhs;
    private OpType operation;
    private Expression rhs;


    public BinaryOperation(Position start, Expression lhs,  OpType operation, Expression rhs) {
        super(start);
        this.lhs = lhs;
        this.rhs = rhs;
        this.operation = operation;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }

    public OpType getOperation() {
        return operation;
    }

    public enum OpType{
        PLUS("+"),
        MINUS("-"),
        TIMES("*"),
        DIV("/"),
        MOD("%");

        private String string;

        OpType(String s) {
            this.string = s;
        }

        @Override
        public String toString() {
            return string;
        }
    }

//    @Override
//    public String toString() {
//        return "BinaryOperation{" +
//                "lhs=" + lhs +
//                ", operation=" + operation +
//                ", rhs=" + rhs +
//                '}';
//    }

        @Override
    public String toString() {
        return "(" + lhs  + " " + operation + " " + rhs + ")";
    }
}
