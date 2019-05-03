package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.IAcceptor;
import klx.parser.acceptor.Sequence;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static klx.Util.onSameLine;
import static klx.parser.ParseError.expected;

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
        {
            Object[] dotStar = __DOT_STAR.accept(parser, onSameLine);
            if (nonNull(dotStar)) {
                return new ImportDecl(pkgName, (Token) dotStar[1]);
            }
        }
        {
            Token item = pkgName.rmLastName();

        }
        return null;
    }

    private static ImportDecl __from(Parser parser) {
        return null;
    }

    private ImportDecl(PackageName pkgName, Token item) {
        this(pkgName, new LinkedList<>(Arrays.asList(item)));
    }

    private ImportDecl(PackageName pkgName, List<Token> items) {
        __packageName = pkgName;
        __items = items;
    }

    private final PackageName __packageName;
    private final List<Token> __items;

}
