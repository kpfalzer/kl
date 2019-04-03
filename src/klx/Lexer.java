package klx;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static klx.Util.Pair;

public class Lexer {
    public Lexer(Path file) throws IOException {
        this(Util.readFile(file), file.toString());
    }

    private Lexer(String text, String fileName) throws IOException {
        this.fileName = fileName;
        __cbuf = text;
        __pos = 0;
        __lnum = 0;
        __col = 0;
    }

    public Lexer(String text) throws IOException {
        this(text, "<none>");
    }

    public Token nextToken() {
        if (__pos <= __length()) {
            if (__pos == __length()) {
                ++__pos;
                return new Token(Token.Type.eEof, fileName, __lnum, __col, "<EOF>");
            }
            __resetMatcher = true;
            Token tok = null;
            for (__PattType pt : __PATTERNS) {
                tok = __tryMatch(pt.v1, pt.v2);
                if (nonNull(tok)) return tok;
            }
            assert false;
        }
        // Already returned EOF
        return null;
    }

    private Token __tryMatch(Pattern patt, Token.Type type) {
        if (__resetMatcher || isNull(__matcher)) {
            __matcher = patt.matcher(__cbuf).region(__pos, __length());
            __resetMatcher = false;
        } else {
            __matcher.usePattern(patt);
        }
        boolean hasMatch = __matcher.lookingAt();
        if (hasMatch) {
            //TODO: is m.start/end relative to 0 or region?
            //TODO: may have to offset by __pos?
            String s = __cbuf.substring(__matcher.start(), __matcher.end());
            Token tok = new Token(type, fileName, __lnum, __col, s);
            __pos = __matcher.end();
            __col += s.length();
            return tok;
        }
        return null;
    }

    private char __la() {
        return __la(0);
    }

    private char __la(int n) {
        n += __pos;
        if (n < __length()) {
            return __cbuf.charAt(n);
        } else {
            return 0x0;
        }
    }

    private int __length() {
        return __cbuf.length();
    }

    public final String fileName;
    private final String __cbuf;
    private int __pos, __lnum, __col;
    private StringBuilder __sbuf;
    private Matcher __matcher;
    private boolean __resetMatcher = true;

    private static final String __TAIL = "(?=(\\p{Punct}|\\s|$))";

    private static final Pattern __INT =
            Pattern.compile("(\\+|\\-)?(\\d([_\\d])*)" + __TAIL);
    private static final Pattern __FLOAT =
            Pattern.compile("(\\+|\\-)?(\\d([_\\d])*)((e|E)|(\\.(e|E)?))(\\+|\\-)?\\d([_\\d])*" + __TAIL);
    private static final Pattern __WHITE_SPACE =
            Pattern.compile("\\s+");

    private static class __PattType extends Pair<Pattern, Token.Type> {
        public __PattType(Pattern patt, Token.Type type) {
            super(patt, type);
        }
    }

    private static __PattType[] __PATTERNS = {
            //order: longest first
            new __PattType(__FLOAT, Token.Type.eFloat),
            new __PattType(__INT, Token.Type.eInt),
            new __PattType(__WHITE_SPACE, Token.Type.eWhiteSpace),

    };

}
