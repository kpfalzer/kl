package klx.parser;

public class ParseError extends RuntimeException {
    public ParseError(String expected, Token found) {
        super(message(expected, found));
    }

    public static void parseError(String expected, Token found) throws ParseError {
        throw new ParseError(expected, found);
    }

    private static String message(String expected, Token found) {
        return found.getLocation() + ": expected '" + expected +"', found '"+found.text+"'";
    }
}
