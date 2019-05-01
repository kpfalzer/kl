package klx.parser;

import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

import klx.parser.Token.EType;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;

import static klx.parser.ParseError.parseError;
import static klx.Util.flatten;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * "package" IDENT ('.' IDENT)*
 */
public class PackageDecl {

    public static PackageDecl parse(Parser parser) {
        if (!parser.laMatches(EType.K_PACKAGE)) {
            return null;
        }
        return new PackageDecl(parser);
    }

    private PackageDecl(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        long lineNum = parser.accept().lineNumber;    //skip "package"
        Object[] items = __PRODUCTION.accept(parser, (Token tok) -> tok.lineNumber == lineNum);
        if (1 > items.length) {
            parseError(__EXPECT, parser.peek());
        }
        __name = flatten(items)
                .map(o -> (Token) o)
                .filter((Token tok) -> tok.type == EType.IDENT)
                .collect(Collectors.toList());
        zeroOrMoreSemiColon(parser);
    }

    private List<Token> __name = Collections.<Token>emptyList();

    private static final String __EXPECT = "package IDENT (. IDENT)*";

    private static final Sequence __PRODUCTION = new Sequence(
            EType.IDENT,
            new Repetition(
                    new Sequence(EType.DOT, EType.IDENT)
            )
    );
}
