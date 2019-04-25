package klx;

public class Token {
    public static enum Type {
        eEof,  //must always be first
        eNewLine,
        eLineComment,
        eBlockComment,
        eDoubleQuotedString,
        eSingleQuotedString,
        eIdent,
        eInt,
        eFloat,
        eWhiteSpace;
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

    static {
        boolean assertsEnabled = false;
        assert assertsEnabled = true;  // Intentional side-effect!!!
        assert Type.eEof.ordinal() == 0;
    }
}
