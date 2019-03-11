package kl;

import java.io.IOException;

public class Lexer {
    public Lexer(String fileName) {
        __fileName = fileName;
    }

    public String fileName() {
        return __fileName;
    }


    private void __ctor() {
        try {
            __cbuf = Util.readFile(fileName()).toCharArray();
            __pos = 0;
            __lnum = 0;
            __col = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String __fileName;
    private char[] __cbuf;
    private long __pos, __lnum, __col;
}
