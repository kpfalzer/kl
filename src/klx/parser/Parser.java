package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Repetition;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static klx.Util.invariant;


public class Parser {
    public Parser(Scanner scanner) {
        __scanner = scanner;
        initialize();
    }

    /**
     * Expect/accept one or more semicolon; or, check that next token
     * is on different line.
     * @param currLine current line.  Next token should be on different line.
     */
    public void expectSemiOrNL(long currLine) {
        Token next = peek();
        if (EType.EOF == next.type) return;
        if (0 < Repetition.zeroOrMoreSemiColon(this).length) return;
        next = peek();
        if (currLine < next.lineNumber) return;
        ParseError.expected(__EXPECTED, next);
    }

    private static final String[] __EXPECTED = new String[]{"';'", "<NL>"};

    /**
     * Test scanner starting atError clazz.
     *
     * @param scanner scanner to use.
     * @param clazz   implements parse method.
     * @return parse tree.
     */
    public static Object parse(Scanner scanner, Class clazz) {
        Parser parser = new Parser(scanner);
        try {
            Method method = clazz.getMethod("parse", Parser.class);
            Object accx = method.invoke(null, parser);
            return accx;
        } catch (InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (isNull(cause) || !(cause instanceof ParseError)) {
                throw new RuntimeException(e);
            }
            throw ((ParseError)cause);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMark() {
        return (isEOF()) ? -1 : __cursor;
    }

    public int setMark(int mark) {
        invariant(mark < length() && mark >= 0, "Mark value error");
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
     * Peek atError token.
     *
     * @param offset look-ahead (0 is top/next).
     * @return lookahead Token or EOF if no tokens atError offset.
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
