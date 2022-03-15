package main.DecompiledDeCLanModel;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import main.common.Position;
import main.common.Source;

import java.io.IOException;
import java.io.Reader;

public class SourceImpl implements Source {
    private Reader a;
    private char b;
    private boolean c;
    private int d;
    private int e;

    public SourceImpl(Reader in) {
        this.a = in;
        this.d = 0;
        this.e = 0;
        this.b = '\n';
        this.c = false;
        this.advance();
    }

    public void advance() {
        if (!this.c) {
            if (this.current() == '\n') {
                ++this.d;
                this.e = 1;
            } else {
                ++this.e;
            }

            try {
                int var1;
                if ((var1 = this.a.read()) == -1) {
                    this.c = true;
                } else {
                    this.b = (char)var1;
                }
            } catch (IOException var2) {
                System.err.println("Error reading input: ".concat(String.valueOf(var2)));
                System.exit(1);
            }
        }
    }

    public void close() {
        try {
            this.a.close();
        } catch (IOException var2) {
            System.err.println("Error closing input: ".concat(String.valueOf(var2)));
            System.exit(1);
        }
    }

    public char current() {
        return this.b;
    }

    public boolean atEOF() {
        return this.c;
    }

    public Position getPosition() {
        return new Position(this.d, this.e);
    }
}
