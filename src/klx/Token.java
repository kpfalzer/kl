package klx;

public class Token {
    public enum EType {
        EOF,
        K_AND,
        K_BOOL,
        K_CLASS,
        K_CONST,
        K_DEF,
        K_ELIF,
        K_ELSE,
        K_EXTENDS,
        K_FALSE,
        K_FLOAT,
        K_FOR,
        K_IMPLEMENTS,
        K_IF,
        K_IMPORT,
        K_INT,
        K_INTERFACE,
        K_NIL,
        K_NOT,
        K_OR,
        K_PACKAGE,
        K_PRIVATE,
        K_PROTECTED,
        K_PUBLIC,
        K_STATIC,
        K_TRUE,
        K_UNLESS,
        K_VAR,
        K_WHILE,
        IDENT,
        LPAREN,
        RPAREN,
        LBRACE,
        RBRACE,
        LBRACK,
        RBRACK,
        SEMICOLON,
        COMMA,
        DOT,
        DOTDOT,
        EQ,
        GT,
        LT,
        NOT,
        COMP,
        QUESTION,
        COLON,
        EQEQ,
        LTEQ,
        GTEQ,
        NOTEQ,
        ANDAND,
        OROR,
        PLUSPLUS,
        MINUSMINUS,
        PLUS,
        MINUS,
        MULT,
        DIV,
        AND,
        OR,
        XOR,
        MOD,
        LSHIFT,
        RSHIFT,
        URSHIFT,
        PLUSEQ,
        MINUSEQ,
        MULTEQ,
        DIVEQ,
        ANDEQ,
        OREQ,
        XOREQ,
        MODEQ,
        LSHIFTEQ,
        RSHIFTEQ,
        URSHIFTEQ,
        STRING_LITERAL,
        CHARACTER_LITERAL,
        REGEX_LITERAL,
    }

    public Token(EType type, String fileName, long lineNumber, long col, String text) {
        this.type = type;
        this.col = col;
        this.lineNumber = lineNumber;
        this.text = text;
        this.fileName = fileName;
    }

    public final EType type;
    public final String fileName;
    public final long lineNumber, col;
    public final String text;
}
