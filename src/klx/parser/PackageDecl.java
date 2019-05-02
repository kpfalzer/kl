package klx.parser;

import klx.parser.Token.EType;

import static java.util.Objects.requireNonNull;
import static klx.parser.acceptor.Repetition.zeroOrMoreSemiColon;

/**
 * "package" PackageName
 */
public class PackageDecl {

    public static PackageDecl parse(Parser parser) {
        if (!parser.laMatches(EType.K_PACKAGE)) {
            return null;
        }
        return new PackageDecl(parser);
    }

    public String[] getName() {
        return __name.getName();
    }

    private PackageDecl(Parser parser) {
        process(parser);
    }

    private void process(Parser parser) {
        long lineNum = parser.accept().lineNumber;    //skip "package"
        __name = PackageName.parse(parser, (Token tok) -> tok.lineNumber == lineNum);
        requireNonNull(__name);
        zeroOrMoreSemiColon(parser);
    }

    private PackageName __name = null;
}
