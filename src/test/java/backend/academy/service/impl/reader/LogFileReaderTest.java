package backend.academy.service.impl.reader;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class LogFileReaderTest {
    @Mock
    private LogFileReader mockLogFileReader;

    @Test
    void givenMockLogFileReaderReturningLogEntry_whenReadSource_thenAsserReturningLogs() throws IOException {
        //Given

        String logEntry1 =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
        String logEntry2 =
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
        String logEntry3 =
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"";
        when(mockLogFileReader.readSource("павр/logs/**/2024-08-31.txt")).thenReturn(
            Stream.of(logEntry1, logEntry2, logEntry3));
        //When
        Stream<String> logStream = mockLogFileReader.readSource("павр/logs/**/2024-08-31.txt");
        String result = logStream.collect(Collectors.joining("\n")).trim();

        //Then
        String expected = logEntry1 + "\n" + logEntry2 + "\n" + logEntry3;
        assertEquals(expected, result);
    }
}
