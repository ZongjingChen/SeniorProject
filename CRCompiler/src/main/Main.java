package main;

import main.AST.Program;
import main.common.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Main {
    private static Source source;
    private static CRLexer lexer;
    private static CRParser parser;
    private static CRChecker checker;
    private static JSCodeGenerator generator;
    private static ErrorLog errorLog = new ErrorLog();

    public static void main(String[] args) {
        String inFileName = args[0];
        String outFileName = args[1];
        initGenerator(inFileName, outFileName);
        compile();
//        testLexer();
//        testParser();
//        testChecker();
//        testGenerator();
    }
    private static void compile() {
        try{
            Program program = parser.parseProgram();
            program.accept(checker);
            program.accept(generator);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        for(ErrorLog.LogItem item : errorLog) {
            System.err.println(item);
        }
    }


    private static void testGenerator() {
        initGenerator("test/testGenerator.cr", "test/testOutput.cr");
        compile();
    }

    private static void initGenerator(String inFileName, String outFileName) {
        initChecker(inFileName);
        generator = new JSCodeGenerator(errorLog, outFileName);
    }

    private static void testChecker() {
        initChecker("test/testChecker.cr");
        try{
            Program program = parser.parseProgram();
            program.accept(checker);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        for(ErrorLog.LogItem item : errorLog) {
            System.err.println(item);
        }
    }

    private static void initChecker(String fileName) {
        initParser(fileName);
        checker = new CRChecker(errorLog);
    }

    private static void testParser() {
        initParser("test/testParser.cr");
        try {
            Program program = parser.parseProgram();
            System.out.println(program);
        }
        catch (ParseException pe) {
            System.err.println(pe.getMessage());
        }

        for (ErrorLog.LogItem item : errorLog) {
            System.err.println(item);
        }
    }

    private static void initParser(String fileName) {
        initLexer(fileName);
        parser = new CRParser(lexer, errorLog);
    }

    public static void testLexer(){
        initLexer("test/testLexer.cr");
        while(lexer.hasNext()) {
            Token token = lexer.next();
            System.out.println(token);
        }
    }

    private static void initLexer(String fileName) {
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file: " + fileName);
            System.exit(1);
        }

        source = new ReaderSource(reader);
//        ErrorLog errorLog = new ErrorLog();
        lexer = new CRLexer(source, errorLog);
    }
}
