package klx;

public class Token {
    public static enum Type {
        eEof(1),
        eNewLine(2),
        eLineComment(3),
        eBlockComment(4),
        eDoubleQuotedString(5),
        eSingleQuotedString(6),
        eIdent(7),
        eInt(8),
        eFloat(9),
        eWhiteSpace(10);

        public final int code;

        private Type(int code) {
            this.code = code;
        }
    }

    public Token(Type type, String fileName, long lineNumber, long col, String text) {
        this.type = type;
        this.col = col;
        this.lineNumber = lineNumber;
        this.text = text;
        this.fileName = fileName;
    }

    public final Type type;
    public final String fileName;
    public final long lineNumber, col;
    public final String text;
}
