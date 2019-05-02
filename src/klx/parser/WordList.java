package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;
import klx.parser.acceptor.Single;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static klx.Util.flatten;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 * %w{IDENT*}
 * tokens can be split across multiple lines, since we have closing }
 */
public class WordList {

    public static WordList parse(Parser parser) {
        if (!parser.laMatches(EType.PCNTWLBRACE)) {
            return null;
        }
        return new WordList(parser);
    }

    public String[] getWords() {
        return __words
                .stream()
                .map(tok -> tok.text)
                .toArray(n -> new String[__words.size()]);
    }

    private WordList(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        Object[] items = __PRODUCTION.accept(parser);
        Token peek = parser.peek();
        if (peek.type != EType.RBRACE) {
            if (isNull(items)) {
                ParseError.expected(new String[]{"IDENT", "}"}, peek);
            }
            ParseError.expected("}", peek);
        }
        parser.accept();
        if (nonNull(items)) {
            __words = flatten(items)
                    .map(o -> (Token) o)
                    .collect(Collectors.toList());
        }
        zeroOrMoreSemiColon(parser);
    }

    private List<Token> __words = Collections.<Token>emptyList();

    private static final Repetition __PRODUCTION = Repetition.zeroOrMore(
            new Single(EType.IDENT)
    );
}
