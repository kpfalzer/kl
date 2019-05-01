package klx.parser.acceptor;

import klx.parser.ParseError;
import klx.parser.Parser;
import klx.parser.Token;
import klx.parser.Token.EType;

import static klx.Util.addToList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static java.util.Objects.isNull;
import static klx.Util.toArray;

public class Sequence implements IAcceptor {
    public Sequence(Object... items) {
        __items = items;
    }

    private final Object[] __items;

    @Override
    public Object[] accept(Parser parser, Predicate predicate) {
        List<Object> accepted = null;
        for (Object acc : __items) {
            if (acc instanceof EType) {
                Token tok = parser.peek();
                EType type = (EType) acc;
                if (type != tok.type) return null;
                if (!predicate.apply(tok)) break;
                accepted = addToList(parser.accept(), accepted);
            } else if (acc instanceof IAcceptor) {
                IAcceptor iacc = (IAcceptor) acc;
                Object[] iaccepted = iacc.accept(parser, predicate);
                if (isNull(iaccepted)) return null;
                accepted = addToList(iaccepted, accepted);
            } else {
                //class instance with static parse()
                Class clazz = (Class) acc;
                try {
                    Method method = clazz.getMethod("parse", Parser.class, Predicate.class);
                    Object accx = method.invoke(null, parser);
                    if (isNull(accx)) return null;
                    accepted = addToList(accx, accepted);
                } catch (ParseError e) {
                    throw e;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return toArray(accepted);
    }
}
