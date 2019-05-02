package klx.parser.acceptor;

import klx.parser.Parser;
import klx.parser.Token;

public class Single implements IAcceptor {
    public Single(Token.EType type) {
        __type = type;
    }

    private final Token.EType __type;

    @Override
    public Object[] accept(Parser parser, Predicate predicate) {
        Token tok = parser.peek();
        if (__type != tok.type || !predicate.apply(tok)) return null;
        return new Object[]{parser.accept()};
    }
}
