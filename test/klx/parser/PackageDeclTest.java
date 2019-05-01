package klx.parser;

import org.junit.Test;

import static java.util.Objects.requireNonNull;

public class PackageDeclTest {

    @Test
    public void parse() {
        String s = "package foo.bar";
        Scanner scanner = Scanner.getStringScanner(s);
        Object ptree = Parser.parse(scanner, PackageDecl.class);
        requireNonNull(ptree);
        //
        s = "package dog\ncat";
        scanner = Scanner.getStringScanner(s);
        ptree = Parser.parse(scanner, PackageDecl.class);
        requireNonNull(ptree);
    }

}