package klx.parser;

import klx.parser.Token.EType;
import klx.parser.acceptor.Alternates;
import klx.parser.acceptor.IAcceptor;
import klx.parser.acceptor.Sequence;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static klx.Util.onSameLine;
import static klx.parser.ParseError.*;

/**
 * Borrowed from python and java.
 * "import" PackageName ('.' ('*' | IDENT))
 * |   "from" PackageName "import" ('*' | IDENT | STRING_LITERAL | WordArray)
 */
public class ImportDecl {

    public static ImportDecl parse(Parser parser) {
        return parse(parser, null);
    }

    public static ImportDecl parse(Parser parser, IAcceptor.Predicate ignored) {
        if (parser.laMatches(EType.K_IMPORT))
            return __import(parser);
        if (parser.laMatches(EType.K_FROM))
            return __from(parser);
        return null;
    }


    private static final Sequence __DOT_STAR = new Sequence(EType.DOT, EType.MULT);

    private static ImportDecl __import(Parser parser) {
        final Token importKwrd = parser.accept();
        long lineNum = importKwrd.lineNumber;
        IAcceptor.Predicate onSameLine = onSameLine(lineNum);
        PackageName pkgName = PackageName.parse(parser, onSameLine);
        if (isNull(pkgName)) {
            expected("PackageName", parser.peek());
        }
        ImportDecl decl = null;
        {
            Object[] dotStar = __DOT_STAR.accept(parser, onSameLine);
            if (nonNull(dotStar)) {
                decl = new ImportDecl(pkgName, (Token) dotStar[1]);
            }
        }
        if (isNull(decl)) {
            if (2 > pkgName.getName().length) {
                error(importKwrd, pkgName.toString() + ": invalid name (missing '.IDENT')");
            }
            Token item = pkgName.rmLastName();
            decl = new ImportDecl(pkgName, item);
        }
        parser.expectSemiOrNL(lineNum);
        return decl;
    }

    private static final Sequence __FROM_IMPORT = new Sequence(
            PackageName.class,
            EType.K_IMPORT
    );

    private static final Alternates __IMPORT_ALTS = new Alternates(
            EType.MULT,
            EType.IDENT,
            EType.STRING_LITERAL,
            WordArray.class
    );

    /**
     * "from" PackageName "import" ('*' | IDENT | STRING_LITERAL | WordArray)
     *
     * @param parser
     * @return
     */
    private static ImportDecl __from(Parser parser) {
        final Token fromKwrd = parser.accept();
        long lineNum = fromKwrd.lineNumber;
        IAcceptor.Predicate onSameLine = onSameLine(lineNum);
        Object[] seq = __FROM_IMPORT.accept(parser, onSameLine);
        if (isNull(seq)) {
            atError(__MSG1, parser.peek());
        }
        PackageName pkgName = (PackageName) seq[0];
        Object[] item = __IMPORT_ALTS.accept(parser, onSameLine);
        if (isNull(item)) {
            expected(__MSG2, parser.peek());
        }
        ImportDecl decl = null;
        if (item[0] instanceof Token) {
            decl = new ImportDecl(pkgName, (Token) item[0]);
        } else {
            WordArray words = (WordArray) item[0];
            decl = new ImportDecl(pkgName, words.getWordTokens().collect(Collectors.toList()));
        }
        parser.expectSemiOrNL(lineNum);
        return decl;
    }

    private static final String __MSG1 = "ImportDecl: from PackageName import ...";
    private static final String[] __MSG2 = new String[]{
            "'*'",
            "IDENT",
            "STRING_LITERAL",
            "WordArray"
    };

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
