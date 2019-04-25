package klx;

public class Token {
    public Token(int type, String fileName, long lineNumber, long col, String text) {
        this.type = type;
        this.col = col;
        this.lineNumber = lineNumber;
        this.text = text;
        this.fileName = fileName;
    }

    public final int type;
    public final String fileName;
    public final long lineNumber, col;
    public final String text;
}
