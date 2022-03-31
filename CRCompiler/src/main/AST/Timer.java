package main.AST;

import java.util.Stack;

public class Timer {
    public enum Scope{
        SIM, SEQ, GLOBAL
    }

    private Stack<Scope> scope;
    private Stack<Double> times;

    public Timer() {
        scope = new Stack<>();
        scope.push(Scope.GLOBAL);
        times = new Stack<>();
        times.push(0.0);
    }

    public Scope getCurrentScope() {
        return scope.peek();
    }

    public double getCurrentTime() {
        return times.peek();
    }

    public void pop() {
        if(scope.peek() != Scope.GLOBAL) {
            scope.pop();
            times.pop();
        }
    }

    public void updateTime(double time) {
        double newTime = times.pop() + time;
        times.push(newTime);
    }

    public void addNewTime(Scope scope, double time){
        this.scope.push(scope);
        times.push(time);
    }

}
