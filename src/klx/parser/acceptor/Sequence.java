package klx.parser.acceptor;

import klx.parser.Parser;
import klx.parser.Token;
import klx.parser.Token.EType;

import static klx.Util.addToList;
import static klx.Util.toArray;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

public class Sequence implements IAcceptor {
    public Sequence(Object... items) {
        __items = items;
    }

    private final Object[] __items;

    @Override
    public Object[] accept(Parser parser) {
        List<Object> accepted = null;
        for (Object acc : __items) {
            if (acc instanceof EType) {
                Token tok = parser.peek();
                if (isNull(tok)) return _emptyArray;
                EType type = (EType) acc;
                if (type != tok.type) return _emptyArray;
                accepted = addToList(parser.accept(), accepted);
            } else if (acc instanceof IAcceptor) {
                IAcceptor iacc = (IAcceptor)acc;
                Object[] iaccepted = iacc.accept(parser);
                if (0 == iaccepted.length) return _emptyArray;
            } else {
                //class instance with static parse()
                Class clazz = (Class) acc;
                try {
                    Method method = clazz.getMethod("parse", Parser.class);
                    Object accx = method.invoke(null, parser);
                    if (isNull(accx)) return _emptyArray;
                    accepted = addToList(accx, accepted);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return toArray(accepted);
    }
}
