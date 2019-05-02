package klx.parser;

import org.junit.Test;

import static java.util.Objects.requireNonNull;
import static klx.Util.invariant;

public class PackageDeclTest {

    @Test
    public void parse() {
        String s = "package foo.bar";
        Scanner scanner = Scanner.getStringScanner(s);
        Object ptree = Parser.parse(scanner, PackageDecl.class);
        requireNonNull(ptree);
        PackageDecl pdecl = (PackageDecl)ptree;
        invariant(2 == pdecl.getName().length, "expected 2 names");

        //
        s = "package dog\ncat";
        scanner = Scanner.getStringScanner(s);
        ptree = Parser.parse(scanner, PackageDecl.class);
        requireNonNull(ptree);
        pdecl = (PackageDecl)ptree;
        invariant(1 == pdecl.getName().length, "expected 1 names");

    }

}