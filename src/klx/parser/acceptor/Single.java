package klx.parser.acceptor;

import klx.parser.Parser;
import klx.parser.Token;

public class Single extends Acceptor {
    public Single(Token.EType type) {
        __type = type;
    }

    private final Token.EType __type;

    @Override
    public Object[] accept(Parser parser, Predicate predicate) {
        _init(parser);
        Token tok = parser.peek();
        if (__type != tok.type || !predicate.apply(tok)) return _fail();
        return new Object[]{parser.accept()};
    }
}
