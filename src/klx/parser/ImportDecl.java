package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.IAcceptor;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static klx.Util.flatten;
import static klx.Util.onSameLine;
import static klx.parser.ParseError.expected;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 * Borrowed from python and java.
 * "import" PackageName ('.' ('*' | IDENT))
 * |   "from" PackageName "import" ('*' | IDENT | STRING_LITERAL | WordArray)
 */
public class ImportDecl {

    public static ImportDecl parse(Parser parser) {
        if (parser.laMatches(EType.K_IMPORT))
            return __import(parser);
        if (parser.laMatches(EType.K_FROM))
            return __from(parser);
        return null;
    }

    private static final Sequence __DOT_STAR = new Sequence(EType.DOT, EType.MULT);

    private static ImportDecl __import(Parser parser) {
        long lineNum = parser.accept().lineNumber;
        IAcceptor.Predicate onSameLine = onSameLine(lineNum);
        PackageName pkgName = PackageName.parse(parser, onSameLine);
        if (isNull(pkgName)) {
            expected("PackageName", parser.peek());
        }
        Object[] dotStar = __DOT_STAR.accept(parser, onSameLine);
        //todo
        return null;
    }

    private static ImportDecl __from(Parser parser) {
        return null;
    }

    private void process(Parser parser) {
        long lineNum = parser.accept().lineNumber;    //skip "package"
        Object[] items = __PRODUCTION.accept(parser, (Token tok) -> tok.lineNumber == lineNum);
        if (isNull(items) || 1 > items.length) {
            expected("IDENT", parser.peek());
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
