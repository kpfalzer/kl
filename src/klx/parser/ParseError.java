package klx.parser;

public class ParseError extends RuntimeException {
    public ParseError(Token loc, String message) {
        super(__message(loc, message));
    }

    public static void expected(String expected, Token found) throws ParseError {
        throw new ParseError(found, "expected '" + expected + "', found '" + found.text + "'");
    }

    public static void expected(String[] expected, Token found) throws ParseError {
        throw new ParseError(found,
                "expected " + expected.toString() + ", found '" + found.text + "'");
    }

    private static String __message(Token loc, String message) {
        return loc.getLocation() + ": " + message;
    }
}
