package klx.parser;

import klx.parser.Token.EType;
import org.junit.Test;

import java.io.IOException;

public class ScannerTest {

    @Test
    public void nextToken() throws IOException {
        Token tok = null;
        while (true) {
            tok = __dut.nextToken();
            if (tok.type == EType.EOF) break;
        }
    }

    private static final String TEST =
            "here \"string\" 'b' 123 true 'b0011 14'd1234 %r{123.*[a-z]+}"
            ;

    private static final Scanner __dut = Scanner.getStringScanner(TEST);
}