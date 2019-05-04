package klx.parser;

import org.junit.Test;

import static java.util.Objects.requireNonNull;

public class ImportDeclTest {

    @Test
    public void parse() {
        String s = "import foo.bar";
        Scanner scanner = Scanner.getStringScanner(s);
        Object ptree = Parser.parse(scanner, ImportDecl.class);
        requireNonNull(ptree);
        ImportDecl pdecl = (ImportDecl) ptree;
        //invariant(2 == pdecl.getName().length, "expected 2 names");

    }
}