package backend.academy.printer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LogReport {

    public void saveReport(StringBuilder report, String filePath) throws IOException {
        Path path = Path.of(filePath);
        try {
            Files.writeString(path, report, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IOException("Ошибка сохранения отчёта", e);
        }
    }
}
