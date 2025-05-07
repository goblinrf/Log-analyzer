package backend.academy.factory;

import backend.academy.service.ReadFromSource;
import backend.academy.service.impl.reader.HttpFetcher;
import backend.academy.service.impl.reader.LogFileReader;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("LSC_LITERAL_STRING_COMPARISON")
public class ReadResourceFactory {
    public static ReadFromSource createReadResource(String res) {
        return switch (res.toLowerCase()) {
            case "url" -> new HttpFetcher();
            case "file" -> new LogFileReader();
            default -> throw new IllegalArgumentException("Unknown input type: " + res);
        };

    }

    private ReadResourceFactory() {
    }
}
