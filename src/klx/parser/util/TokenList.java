package klx.parser.util;

import klx.parser.Token;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static klx.Util.flatten;

public class TokenList {

    public TokenList(Object[] items, Predicate<Token> filter) {
        __tokens = flatten(items)
                .map(o -> (Token) o)
                .filter(filter)
                .collect(Collectors.toList());
    }

    public String[] asString() {
        return asStream()
                .map(tok -> tok.text)
                .toArray(n -> new String[__tokens.size()]);
    }

    public Stream<Token> asStream() {
        return __tokens.stream();
    }

    private final List<Token> __tokens;

}
