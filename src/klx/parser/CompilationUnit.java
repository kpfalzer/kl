package klx.parser;

/**
 * Toplevel nonterminal.
 */
public class CompilationUnit {
    public CompilationUnit(Scanner scanner) {
        __parser = new Parser(scanner);
    }

    private final Parser __parser;
}
