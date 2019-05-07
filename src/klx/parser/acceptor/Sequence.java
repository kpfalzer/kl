package klx.parser.acceptor;

import klx.parser.Parser;
import klx.parser.Token;
import klx.parser.Token.EType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static java.util.Objects.isNull;
import static klx.Util.addToList;
import static klx.Util.toArray;

public class Sequence extends Acceptor {
    public Sequence(Object... items) {
        __items = items;
    }

    private final Object[] __items;

    @Override
    public Object[] accept(Parser parser, Predicate predicate) {
        _init(parser);
        List<Object> accepted = null;
        for (Object acc : __items) {
            if (acc instanceof EType) {
                Token tok = parser.peek();
                EType type = (EType) acc;
                if (type != tok.type) return _fail();
                if (!predicate.apply(tok)) break;
                accepted = addToList(parser.accept(), accepted);
            } else if (acc instanceof Acceptor) {
                Acceptor iacc = (Acceptor) acc;
                Object[] iaccepted = iacc.accept(parser, predicate);
                if (isNull(iaccepted)) return _fail();
                accepted = addToList(iaccepted, accepted);
            } else {
                //class instance with static parse()
                Class clazz = (Class) acc;
                try {
                    Method method = clazz.getMethod("parse", Parser.class, Predicate.class);
                    Object accx = method.invoke(null, parser, predicate);
                    if (isNull(accx)) return _fail();
                    accepted = addToList(accx, accepted);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return toArray(accepted);
    }
}
