package klx.parser.acceptor;

import klx.parser.Parser;
import klx.parser.Token;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Objects.isNull;

public class Alternates implements IAcceptor {
    public Alternates(Object... items) {
        __items = items;
    }

    private final Object[] __items;

    @Override
    public Object[] accept(Parser parser, Predicate predicate) {
        for (Object acc : __items) {
            if (acc instanceof Token.EType) {
                Token tok = parser.peek();
                Token.EType type = (Token.EType) acc;
                if (type != tok.type) continue;
                if (!predicate.apply(tok)) continue;
                return new Object[]{parser.accept()};
            } else if (acc instanceof IAcceptor) {
                IAcceptor iacc = (IAcceptor) acc;
                Object[] iaccepted = iacc.accept(parser, predicate);
                if (isNull(iaccepted)) continue;
                return iaccepted;
            } else {
                //class instance with static parse()
                Class clazz = (Class) acc;
                try {
                    Method method = clazz.getMethod("parse", Parser.class, Predicate.class);
                    Object accx = method.invoke(null, parser, predicate);
                    if (isNull(accx)) continue;
                    return new Object[]{accx};
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return null;
    }
}
