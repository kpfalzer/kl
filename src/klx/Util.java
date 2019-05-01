package klx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class Util {
    public static Stream<Object> flatten(Object[] array) {
        return Arrays.stream(array)
                .flatMap(o -> o instanceof Object[]? flatten((Object[])o): Stream.of(o));
    }

    public static void invariant(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Invariant failed: " + msg);
        }
    }

    public static <T> List<T> addToList(T ele, List<T> list) {
        if (isNull(list)) {
            list = new LinkedList<>();
        }
        list.add(ele);
        return list;
    }

    public static <T> Object[] toArray(List<T> list) {
        return isNull(list) ? null : list.toArray();
    }

    public static String readFile(String fileName) throws IOException {
        return readFile(Paths.get(fileName));
    }

    public static String readFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file));
    }

    public static class Pair<T1, T2> {
        public Pair() {
            v1 = null;
            v2 = null;
        }

        public Pair(T1 v1, T2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public T1 v1;
        public T2 v2;

    }
}
