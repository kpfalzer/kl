package klx.parser.acceptor;

import klx.parser.Token;
import klx.parser.Parser;

import java.util.function.Function;

public abstract class Acceptor {
    /**
     * Match parser tokens to this acceptor.
     *
     * @param parser
     * @return match sequence or empty array.
     */
    public Object[] accept(Parser parser) {
        return accept(parser, _ALWAYS_TRUE);
    }

    public static interface Predicate extends Function<Token, Boolean> {

    }

    public abstract Object[] accept(Parser parser, Predicate predicate);

    static final Object[] _emptyArray = new Object[0];

    static Predicate _ALWAYS_TRUE = (Token tok) -> true;

    private int __mark = -1;
    protected Parser __parser = null;

    protected void _init(Parser parser) {
        __parser = parser;
        __mark = __parser.getMark();
    }

    protected Object[] _fail() {
        if (0 <= __mark) {
            __parser.setMark(__mark);
        }
        return null;
    }

}
