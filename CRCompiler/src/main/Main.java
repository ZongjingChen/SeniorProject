package main;

import main.DecompiledDeCLanModel.TokenFactoryImpl;
import main.common.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Main {
    private static String fileName;
    private static Source source;
    private static TokenFactory tokenFactory;
    private static CRLexer lexer;

    public static void main(String[] args) {
        testLexer();
    }

    public static void testLexer(){
        initLexer("test/test.cr");
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
        tokenFactory = new TokenFactoryImpl();
        lexer = new CRLexer(source, tokenFactory);
    }
}
