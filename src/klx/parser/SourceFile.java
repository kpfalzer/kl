package klx.parser;

import klx.parser.acceptor.Acceptor;

/**
 * Top-most non-terminal of klx grammar.
 */
public class SourceFile {
    public static SourceFile parse(Parser parser) {
        return parse(parser, null);
    }

    public static SourceFile parse(Parser parser, Acceptor.Predicate ignored) {
        return null;
    }


}
