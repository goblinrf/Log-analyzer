package backend.academy.service.impl.reader;

import backend.academy.service.ReadFromSource;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LogFileReader implements ReadFromSource {

    @Override
    public Stream<String> readSource(String pathPattern) throws IOException {

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pathPattern);
        List<Path> matchedPaths = new ArrayList<>();

        Path startPath = Paths.get("src/main/resources");

        Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                if (pathMatcher.matches(file)) {
                    matchedPaths.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        return matchedPaths.stream().flatMap(this::linesFromFile);
    }

    private Stream<String> linesFromFile(Path file) {
        try {
            return Files.lines(file);
        } catch (IOException e) {
            return Stream.empty();
        }
    }
}
