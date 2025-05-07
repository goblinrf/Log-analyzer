package backend.academy.service;

import java.io.IOException;
import java.util.stream.Stream;

public interface ReadFromSource {
    Stream<String> readSource(String path) throws IOException;
}
