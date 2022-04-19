package main.common;

import java.util.Stack;

public class Timer {
    public enum Scope{
        SIM, SEQ, GLOBAL
    }

    private Stack<Scope> scopes;
    private Stack<Double> times;

    public Timer() {
        scopes = new Stack<>();
        scopes.push(Scope.GLOBAL);
        times = new Stack<>();
        times.push(0.0);
    }

    public Scope getCurrentScope() {
        return scopes.peek();
    }

    public double getCurrentTime() {
        return times.peek();
    }

    public void pop() {
        if(scopes.peek() != Scope.GLOBAL) {
            scopes.pop();
            times.pop();
        }
    }

    public void updateTime(double time) {
        double newTime = times.pop() + time;
        times.push(newTime);
    }

    public void addNewTime(Scope scope, double time){
        scopes.push(scope);
        times.push(time);
    }

}
