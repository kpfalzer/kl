package klx.parser.acceptor;

import klx.parser.Parser;

import java.util.List;

import static klx.Util.addToList;
import static klx.Util.toArray;

public class Repetition implements IAcceptor {
    public Repetition(IAcceptor acceptor) {
        __acc = acceptor;
    }

    private final IAcceptor __acc;

    @Override
    public Object[] accept(Parser parser) {
        List<Object> accepted = null;
        while (true) {
            Object[] acc = __acc.accept(parser);
            if (0 == acc.length) break;
            accepted = addToList(acc, accepted);
        }
        return toArray(accepted);
    }
}
