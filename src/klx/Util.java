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
}
