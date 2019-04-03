package klx;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
            Token tok = __int();
            if (nonNull(tok)) return tok;
            tok = __float();
            if (nonNull(tok)) return tok;
            assert false;
        }
        // Already returned EOF
        return null;
    }

    private Token __tryMatch(Pattern patt, Token.Type type) {
        return __tryMatch(patt, type, false);
    }

    private Token __tryMatch(Pattern patt, Token.Type type, boolean reset) {
        if (reset || isNull(__matcher)) {
            __matcher = patt.matcher(__cbuf).region(__pos, __length());
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

    private Token __int() {
        return __tryMatch(__INT, Token.Type.eInt);
    }

    private Token __float() {
        return __tryMatch(__FLOAT, Token.Type.eFloat);
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

    private static final String __TAIL = "(?=(\\p{Punct}|\\s))";

    private static final Pattern __INT =
            Pattern.compile("(\\+\\-)?(\\d([_\\d])*)" + __TAIL);
    private static final Pattern __FLOAT =
            Pattern.compile("(\\+\\-)?(\\d([_\\d])*)(\\.(e|E)?(\\+|\\-)?(\\d([_\\d])*))" + __TAIL);

}
