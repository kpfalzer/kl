package klx.parser.acceptor;

import klx.parser.Parser;
import klx.parser.Token;
import klx.parser.Token.EType;

import java.util.List;

import static klx.Util.addToList;
import static klx.Util.toArray;

public class Repetition implements IAcceptor {
    public Repetition(IAcceptor acceptor) {
        __acc = acceptor;
    }

    private final IAcceptor __acc;

    @Override
    public Object[] accept(Parser parser, Predicate predicate) {
        List<Object> accepted = null;
        while (true) {
            Object[] acc = __acc.accept(parser, predicate);
            if (0 == acc.length) break;
            accepted = addToList(acc, accepted);
        }
        return toArray(accepted);
    }

    public static Object[] zeroOrMoreSemiColon(Parser parser) {
        List<Object> semis = null;
        Token tok;
        while (true) {
            tok = parser.peek();
            if (tok.type != EType.SEMICOLON) break;
            semis = addToList(parser.accept(), semis);
        }
        return toArray(semis);
    }
}
