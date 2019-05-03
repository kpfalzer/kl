package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.IAcceptor;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static klx.Util.flatten;

/**
 * IDENT ('.' IDENT)*
 */
public class PackageName {

    public static PackageName parse(Parser parser, IAcceptor.Predicate predicate) {
        if (!parser.laMatches(EType.IDENT)) {
            return null;
        }
        return new PackageName(parser, predicate);
    }

    public String[] getName() {
        return __name
                .stream()
                .map(tok -> tok.text)
                .toArray(n -> new String[__name.size()]);
    }

    public String toString() {
        return String.join(".", getName());
    }

    public Token rmLastName() {
        return __name.remove(__name.size()-1);
    }

    private PackageName(Parser parser, IAcceptor.Predicate predicate) {
        process(parser, predicate);
    }

    private void process(Parser parser, IAcceptor.Predicate predicate) {
        Object[] items = __PRODUCTION.accept(parser, predicate);
        if (isNull(items) || 1 > items.length) {
            ParseError.expected("IDENT", parser.peek());
        }
        __name = flatten(items)
                .map(o -> (Token) o)
                .filter((Token tok) -> tok.type == EType.IDENT)
                .collect(Collectors.toList());
    }

    private List<Token> __name = null;

    private static final Sequence __PRODUCTION = new Sequence(
            EType.IDENT,
            Repetition.zeroOrMore(
                    new Sequence(EType.DOT, EType.IDENT)
            )
    );
}
