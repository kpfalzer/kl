package kl;

public class Token {
    public static enum Type {
        eEof,
        eLineComment,
        eBlockComment,
        eDoubleQuotedString,
        eSingleQuotedString,
        eIdent,
        eInt,
        eFloat,
    }

    public Token(Type type, String fileName, long lineNumber, long col, String text) {
        __type = type;
        __col = col;
        __lineNumber = lineNumber;
        __text = text;
        __fileName = fileName;

    }

    private final Type __type;
    private final String __fileName;
    private final long __lineNumber, __col;
    private final String __text;
}
