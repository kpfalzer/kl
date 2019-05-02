package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static klx.Util.flatten;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 *      "import" PackageName ('.' ('*' | IDENT))
 *  |   "from" PackageName "import" ('*' | IDENT | STRING_LITERAL)
 *  |   "from" PackageName "import" WordArray
 */
public class ImportDecl {

    public static ImportDecl parse(Parser parser) {
        if (!parser.laMatches(EType.K_PACKAGE)) {
            return null;
        }
        return new ImportDecl(parser);
    }

    public String[] getName() {
        return __name
                .stream()
                .map(tok -> tok.text)
                .toArray(n -> new String[__name.size()]);
    }

    private ImportDecl(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        long lineNum = parser.accept().lineNumber;    //skip "package"
        Object[] items = __PRODUCTION.accept(parser, (Token tok) -> tok.lineNumber == lineNum);
        if (isNull(items) || 1 > items.length) {
            ParseError.expected("IDENT", parser.peek());
        }
        __name = flatten(items)
                .map(o -> (Token) o)
                .filter((Token tok) -> tok.type == EType.IDENT)
                .collect(Collectors.toList());
        zeroOrMoreSemiColon(parser);
    }

    private List<Token> __name = Collections.<Token>emptyList();

    private static final Sequence __PRODUCTION = new Sequence(
            EType.IDENT,
            Repetition.zeroOrMore(
                    new Sequence(EType.DOT, EType.IDENT)
            )
    );
}
