package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class FirstLastTimeReportTest {
private FirstLastTimeReport firstLastTimeReport;
    @BeforeEach
    void setup() {
        firstLastTimeReport = new FirstLastTimeReport();
    }
    @Test
    @DisplayName("Должен корректно формировать отчет по первой и последней дате запроса")
    public void givenResult_whenGenerateGeneralInformationReport_thenAssertReportContent() {
        // Given
        LogAnalysisResult result = new LogAnalysisResult(
            Set.of("192.168.1.1", "192.168.1.2"), 200,
            Map.of("/resource", 100L), LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 12, 31), Map.of(200, 150L),
            500, 950
        );
        // When
        String report = firstLastTimeReport.generateReport(result);

        // Then
        assertTrue(report.contains("#### Время первого и последнего запроса"), "Отчет должен содержать заголовок 'Время первого и последнего запроса'");
        assertTrue(report.contains("|\n2023-01-01 |"), "Отчет должен содержать дату первого запроса");
        assertTrue(report.contains("| 2023-12-31 |"), "Отчет должен содержать дату последнего запроса");


    }
}
