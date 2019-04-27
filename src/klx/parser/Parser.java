package klx.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static klx.Util.invariant;

import klx.parser.Token.EType;


public class Parser {
    public Parser(Scanner scanner) {
        __scanner = scanner;
        initialize();
    }

    public int getMark() {
        invariant(!isEOF(), "Cannot getMark() if EOF");
        return __cursor;
    }

    public int setMark(int mark) {
        invariant(mark < length(), "Mark value error");
        __cursor = mark;
        checkEOF();
        return __cursor;
    }

    public boolean isEOF() {
        return __isEOF;
    }

    public boolean laMatches(EType type, int la) {
        return peek(la).type == type;
    }

    public boolean laMatches(EType type) {
        return laMatches(type, 0);
    }

    /**
     * Peek at token.
     *
     * @param offset look-ahead (0 is top/next).
     * @return lookahead Token or EOF if no tokens at offset.
     */
    public Token peek(int offset) {
        int n = __cursor + offset;
        return (n < length()) ? __tokens[n] : __tokens[length() - 1];
    }

    private int length() {
        return __tokens.length;
    }

    public Token peek() {
        return peek(0);
    }

    /**
     * Accept current token.
     *
     * @return current token or null if no more.
     */
    public Token accept() {
        Token tok = __tokens[__cursor];
        if (!isEOF()) {
            __cursor++;
            checkEOF();
        }
        return tok;
    }

    private void initialize() {
        List<Token> tokens = new LinkedList<>();
        Token tok = null;
        while (true) {
            try {
                tok = __scanner.nextToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            tokens.add(tok);
            if (EType.EOF == tok.type) break;
        }
        __tokens = tokens.toArray(new Token[0]);
        invariant(0 < length() && EType.EOF == __tokens[length() - 1].type, "Expected EOF");
        checkEOF();
    }

    private boolean checkEOF() {
        __isEOF = EType.EOF == __tokens[__cursor].type;
        return __isEOF;
    }

    private boolean __isEOF = false;
    private final Scanner __scanner;
    private Token __tokens[] = null;
    private int __cursor = 0;
}
