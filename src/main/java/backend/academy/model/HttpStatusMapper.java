package backend.academy.model;

import backend.academy.utils.JsonReader;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.Map;

public class HttpStatusMapper {
    private final static Map<Integer, String> STATUS_DESCRIPTIONS;

    static {
        try {
            STATUS_DESCRIPTIONS = JsonReader.readJsonFile("src/main/resources/http_status_codes.json",
                new TypeToken<Map<Integer, String>>() {
                }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStatusDescription(Integer statusCode) {
        return STATUS_DESCRIPTIONS.getOrDefault(statusCode, "Unknown Status Code");
    }

    private HttpStatusMapper() {

    }
}
