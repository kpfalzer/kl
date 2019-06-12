package klx.parser;

import klx.parser.acceptor.Acceptor;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;
import klx.parser.util.TokenList;

import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 * IDENT (COMMA IDENT)*
 */
public class IdentList {
    public static IdentList parse(Parser parser) {
        return parse(parser, null);
    }

    public static IdentList parse(Parser parser, Acceptor.Predicate ignored) {
        if (!parser.laMatches(Token.EType.IDENT)) {
            return null;
        }
        return new IdentList(parser);
    }

    public String[] getIdents() {
        return __idents.asString();
    }

    public Stream<Token> getIdentTokens() {
        return __idents.asStream();
    }

    private IdentList(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        Object[] items = __PRODUCTION.accept(parser);
        if (isNull(items)) {
            ParseError.atError(__MSG, parser.peek());
        }
        __idents = new TokenList(items, (Token tok) -> tok.type == Token.EType.IDENT);
        zeroOrMoreSemiColon(parser);
    }

    private TokenList __idents = null;

    private static final String __MSG = "IdentList: IDENT (',' IDENT)*";

    private static final Sequence __PRODUCTION = new Sequence(
            Token.EType.IDENT,
            Repetition.zeroOrMore(
                    new Sequence(Token.EType.COMMA, Token.EType.IDENT)
            )
    );

}
