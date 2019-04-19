package klx;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LexerTest {

    @Before
    public void setUp() throws Exception {
        __lexer = new Lexer(__TEXT);
    }

    @Test
    public void nextToken() {
        Token tok = null;
        boolean rval = true;
        while (true) {
            tok = __lexer.nextToken();
            if (tok.type == Token.Type.eEof)
                break;
        }
    }

    private static final String __TEXT =
            "-12 +34.345\n"
                    + "123 456 -12e09";
    private klx.Lexer __lexer;

}