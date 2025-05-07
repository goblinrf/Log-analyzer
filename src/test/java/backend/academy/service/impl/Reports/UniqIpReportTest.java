package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqIpReportTest {

    private UniqIpReport uniqIpReport;

    @BeforeEach
    void setup() {
        uniqIpReport = new UniqIpReport();
    }

    @Test
    @DisplayName("Должен корректно формировать отчет по уникальным IP")
    void givenResult_whenGenerateUniqIpReport_thenAssertReportContent() {
        // Given
        LogAnalysisResult result = new LogAnalysisResult(
            Set.of("192.168.1.1", "192.168.1.2"), 200, null, null, null, null, 0, 0
        );

        // When
        String report = uniqIpReport.generateReport(result);

        // Then
        assertTrue(report.contains("#### Уникальные ip"), "Отчет должен содержать заголовок 'Уникальные ip'");
        assertTrue(report.contains("Общее количество уникальных IP-адресов: |2"), "Отчет должен содержать количество уникальных IP-адресов");
        assertTrue(report.contains("Общее количество запросов: |200"), "Отчет должен содержать общее количество запросов");
    }
}
