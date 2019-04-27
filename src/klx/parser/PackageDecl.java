package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Repetition;
import klx.parser.acceptor.Sequence;

import java.util.LinkedList;
import java.util.List;

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
        long lineNum = parser.accept().lineNumber;    //skip "package"
        //todo: automata IDENT (. IDENT)* and on same line
    }

    private List<Token> __name = new LinkedList<>();

    private static final Sequence __PRODUCTION = new Sequence(
            EType.IDENT,
            new Repetition(
                new Sequence(EType.DOT, EType.IDENT)
            )
    );
}
