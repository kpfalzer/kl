package klx.parser;

import org.junit.Test;

import static java.util.Objects.requireNonNull;
import static klx.Util.invariant;
import static org.junit.Assert.*;

public class WordArrayTest {

    @Test
    public void parse() {
        {
            String s = "%w{foo bar}";
            Scanner scanner = Scanner.getStringScanner(s);
            Object ptree = Parser.parse(scanner, WordArray.class);
            requireNonNull(ptree);
            WordArray pdecl = (WordArray) ptree;
            invariant(2 == pdecl.getWords().length, "expected 2 names");
        }

        {
            String s = "%w{\n\n7\n};";
            Scanner scanner = Scanner.getStringScanner(s);
            Object ptree = Parser.parse(scanner, WordArray.class);
        }
    }
}