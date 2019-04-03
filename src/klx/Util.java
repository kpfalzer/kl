package klx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Util {
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
