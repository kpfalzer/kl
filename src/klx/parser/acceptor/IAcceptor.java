package klx.parser.acceptor;

import klx.parser.Token;
import klx.parser.Parser;

import java.util.function.Function;

public interface IAcceptor {
    /**
     * Match parser tokens to this acceptor.
     *
     * @param parser
     * @return match sequence or empty array.
     */
    default public Object[] accept(Parser parser) {
        return accept(parser, _ALWAYS_TRUE);
    }

    public static interface Predicate extends Function<Token, Boolean> {

    }

    public Object[] accept(Parser parser, Predicate predicate);

    static final Object[] _emptyArray = new Object[0];

    static Predicate _ALWAYS_TRUE = (Token tok) -> true;
}
