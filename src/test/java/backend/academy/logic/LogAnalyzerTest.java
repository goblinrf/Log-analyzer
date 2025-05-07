package backend.academy.logic;

import backend.academy.model.LogAnalysisResult;
import backend.academy.model.LogRecord;
import backend.academy.service.LogFilter;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogAnalyzerTest {
    @Mock
    private LogFilter filterMock;
    @InjectMocks
    private LogAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer.addFilter(filterMock);
    }

    private LogRecord createLogRecord(String ip, LocalDate date, String resource, int statusCode, int responseSize) {
        return new LogRecord(ip, "user", date, "GET", resource, statusCode, responseSize, null, "User-Agent");
    }

    @Test
    @DisplayName("Должен корректно считать уникальные IP, количество запросов и средний размер ответа")
    void givenLogs_whenAnalyze_thenCalculateCorrectStats() {
        // Given
        LocalDate date1 = LocalDate.of(2023, 6, 1);
        LocalDate date2 = LocalDate.of(2023, 6, 2);

        LogRecord log1 = createLogRecord("127.0.0.1", date1, "/resource1", 200, 500);
        LogRecord log2 = createLogRecord("127.0.0.2", date2, "/resource2", 404, 1000);
        LogRecord log3 = createLogRecord("127.0.0.1", date1, "/resource1", 200, 200);

        when(filterMock.apply(log1)).thenReturn(true);
        when(filterMock.apply(log2)).thenReturn(true);
        when(filterMock.apply(log3)).thenReturn(true);

        // When
        analyzer.processRecord(log1);
        analyzer.processRecord(log2);
        analyzer.processRecord(log3);
        LogAnalysisResult result = analyzer.generateFinalReport();
        // Then
        assertEquals(2, result.uniqueIPs().size(), "Должно быть 2 уникальных IP-адреса.");
        assertEquals(3, result.requestCount(), "Должно быть 3 запроса.");
        assertEquals(566.6666666666666, result.averageResponseSize(), 0.01, "Средний размер ответа должен быть 566");
    }

    @Test
    @DisplayName("Должен корректно считать количество ресурсов и статус-кодов")
    void givenLogs_whenAnalyze_thenCountResourcesAndStatusCodes() {
        // Given
        LocalDate date1 = LocalDate.of(2023, 6, 1);

        LogRecord log1 = createLogRecord("127.0.0.1", date1, "/resource1", 200, 500);
        LogRecord log2 = createLogRecord("127.0.0.2", date1, "/resource2", 404, 1000);
        LogRecord log3 = createLogRecord("127.0.0.3", date1, "/resource1", 200, 200);

        when(filterMock.apply(log1)).thenReturn(true);
        when(filterMock.apply(log2)).thenReturn(true);
        when(filterMock.apply(log3)).thenReturn(true);

        // When
        analyzer.processRecord(log1);
        analyzer.processRecord(log2);
        analyzer.processRecord(log3);
        LogAnalysisResult result = analyzer.generateFinalReport();

        // Then
        assertEquals(Map.of("/resource1", 2L, "/resource2", 1L), result.resourceCounts(),
            "Неверное количество запросов на ресурс.");
        assertEquals(Map.of(200, 2L, 404, 1L), result.statusCodes(), "Неверное количество запросов с кодом статуса.");
    }

    @Test
    @DisplayName("Должен корректно определять первую и последнюю дату запросов")
    void givenLogs_whenAnalyze_thenDetermineFirstAndLastRequestTime() {
        // Given
        LocalDate date1 = LocalDate.of(2023, 6, 1);
        LocalDate date2 = LocalDate.of(2023, 6, 5);
        LocalDate date3 = LocalDate.of(2023, 6, 3);

        LogRecord log1 = createLogRecord("127.0.0.1", date1, "/resource1", 200, 500);
        LogRecord log2 = createLogRecord("127.0.0.2", date2, "/resource2", 200, 1000);
        LogRecord log3 = createLogRecord("127.0.0.3", date3, "/resource1", 404, 300);

        when(filterMock.apply(log1)).thenReturn(true);
        when(filterMock.apply(log2)).thenReturn(true);

        // When
        analyzer.processRecord(log1);
        analyzer.processRecord(log2);
        analyzer.processRecord(log3);
        LogAnalysisResult result = analyzer.generateFinalReport();

        // Then
        assertEquals(date1, result.firstRequestTime(), "Неверная дата первого запроса.");
        assertEquals(date2, result.lastRequestTime(), "Неверная дата последнего запроса.");
    }

    @Test
    @DisplayName("Должен корректно вычислять 95-й процентиль размера ответа")
    void givenLogs_whenAnalyze_thenCalculate95thPercentile() {
        // Given
        LocalDate date = LocalDate.of(2023, 6, 1);

        LogRecord log1 = createLogRecord("127.0.0.1", date, "/resource1", 200, 100);
        LogRecord log2 = createLogRecord("127.0.0.2", date, "/resource2", 200, 500);
        LogRecord log3 = createLogRecord("127.0.0.3", date, "/resource3", 200, 900);

        when(filterMock.apply(log1)).thenReturn(true);
        when(filterMock.apply(log2)).thenReturn(true);
        when(filterMock.apply(log3)).thenReturn(true);

        // When
        analyzer.processRecord(log1);
        analyzer.processRecord(log2);
        analyzer.processRecord(log3);
        LogAnalysisResult result = analyzer.generateFinalReport();

        // Then
        assertEquals(860, result.percentile95(), 0.01, "95-й процентиль должен быть равен 833.");
    }
}
