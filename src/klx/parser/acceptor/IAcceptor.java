package klx.parser.acceptor;

import klx.parser.Parser;

public interface IAcceptor {
    /**
     * Match parser tokens to this acceptor.
     * @param parser
     * @return match sequence or empty array.
     */
    public Object[] accept(Parser parser);

    static final Object[] _emptyArray = new Object[0];
}
