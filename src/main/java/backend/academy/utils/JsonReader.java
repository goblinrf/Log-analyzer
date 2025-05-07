package backend.academy.utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonReader {
    public static <T> T readJsonFile(String filePath, Type type) throws IOException {
        Path path = Paths.get(filePath);
        try {
            String reader = Files.readString(path);
            Gson gson = new Gson();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new IOException("Ошибка чтения из файла", e);
        }
    }

    private JsonReader() {
    }
}
