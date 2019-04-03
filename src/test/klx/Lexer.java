package test.klx;

import java.io.IOException;

import klx.Token;

public class Lexer {
    private Lexer(String text) {
        try {
            __lexer = new klx.Lexer(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean testme() {
        Token tok = null;
        boolean rval = true;
        while (true) {
            tok = __lexer.nextToken();
            if (tok.type == Token.Type.eEof)
                break;

        }
        return rval;
    }

    private klx.Lexer __lexer;

    public static void main(String argv[]) {
        Lexer dut = new Lexer(__TEXT);
        dut.testme();
    }

    private static final String __TEXT =
            "-12 +34.345\n"
                    + "123 456";
}
