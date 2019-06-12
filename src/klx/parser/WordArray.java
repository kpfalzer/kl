package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Acceptor;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;
import klx.parser.acceptor.Single;
import klx.parser.util.TokenList;

import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 * Word array (borrowed from Ruby)
 * %w{IDENT*}
 * tokens can be split across multiple lines, since we have closing }
 */
public class WordArray {

    public static WordArray parse(Parser parser) {
        return parse(parser, null);
    }

    public static WordArray parse(Parser parser, Acceptor.Predicate ignored) {
        if (!parser.laMatches(EType.PCNTWLBRACE)) {
            return null;
        }
        return new WordArray(parser);
    }

    public String[] getWords() {
        return __words.asString();
    }

    public Stream<Token> getWordTokens() {
        return __words.asStream();
    }

    private WordArray(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        Object[] items = __PRODUCTION.accept(parser);
        if (isNull(items)) {
            ParseError.atError(__MSG, parser.peek());
        }
        __words = new TokenList(items, (Token tok) -> tok.type == EType.IDENT);
        zeroOrMoreSemiColon(parser);
    }

    private TokenList __words = null;

    private static final String __MSG = "WordArray: %w{ IDENT* }";

    private static final Sequence __PRODUCTION = new Sequence(
            EType.PCNTWLBRACE,
            Repetition.zeroOrMore(
                    new Single(EType.IDENT)
            ),
            EType.RBRACE
    );

}
