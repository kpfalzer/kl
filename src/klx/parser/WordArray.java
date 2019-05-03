package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;
import klx.parser.acceptor.Single;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static klx.Util.flatten;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 * Word array (borrowed from Ruby)
 * %w{IDENT*}
 * tokens can be split across multiple lines, since we have closing }
 */
public class WordArray {

    public static WordArray parse(Parser parser) {
        if (!parser.laMatches(EType.PCNTWLBRACE)) {
            return null;
        }
        return new WordArray(parser);
    }

    public String[] getWords() {
        return __words
                .stream()
                .map(tok -> tok.text)
                .toArray(n -> new String[__words.size()]);
    }

    private WordArray(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        Object[] items = __PRODUCTION.accept(parser);
        if (isNull(items)) {
            ParseError.atError(__MSG, parser.peek());
        }
        __words = flatten(items)
                .map(o -> (Token) o)
                .filter((Token tok) -> tok.type == EType.IDENT)
                .collect(Collectors.toList());
        zeroOrMoreSemiColon(parser);
    }

    private List<Token> __words = null;

    private static final String __MSG = "WordArray: %w{ IDENT* }";

    private static final Sequence __PRODUCTION = new Sequence(
            EType.PCNTWLBRACE,
            Repetition.zeroOrMore(
                    new Single(EType.IDENT)
            ),
            EType.RBRACE
    );

}
