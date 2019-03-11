package kl;

import java.io.IOException;

public class Lexer {
    public Lexer(String fileName) throws IOException {
        __fileName = fileName;
        __ctor();
    }

    public String fileName() {
        return __fileName;
    }

    public Token nextToken() {
        if (__pos < __length()) {
            if (__pos == __length()) {
                __accept();
                return new Token(Token.Type.eEof, fileName(), __lnum, __col, "<EOF>");
            }
            char ch = __la();
            __sbuf = new StringBuilder();
            if ('0' <= ch && ch <= '9') {
                return __number();
            }
        }
        // Already returned EOF
        return null;
    }

    private Token __number() {
        return null; //todo
    }

    private void __accept() {
        __accept(0);
    }

    private void __accept(int n) {
        char ch;
        for (int i = 0; i < n; ++i) {
            ch = __la();
            __sbuf.append(ch);
            ++__pos;
            if (ch == '\n') {
                ++__lnum;
                __col = 0;
            } else {
                ++__col;
            }
        }
    }

    private char __la() {
        return __la(0);
    }

    private char __la(int n) {
        n += __pos;
        if (n < __length()) {
            return __cbuf[n];
        } else {
            return 0x0;
        }
    }

    private int __length() {
        return __cbuf.length;
    }

    private void __ctor() throws IOException {
        __cbuf = Util.readFile(fileName()).toCharArray();
        __pos = 0;
        __lnum = 0;
        __col = 0;
    }

    private String __fileName;
    private char[] __cbuf;
    private int __pos, __lnum, __col;
    private StringBuilder __sbuf;

    static {
        assert '0' < '9';
    }
}
