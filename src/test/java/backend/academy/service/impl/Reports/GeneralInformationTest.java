package backend.academy.service.impl.Reports;

import backend.academy.logic.parser.ParseParams;
import backend.academy.model.LogAnalysisResult;
import backend.academy.model.LogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneralInformationTest {

    private GeneralInformation generalInformation;

    @BeforeEach
    void setup() {
        ParseParams params = new ParseParams();
        params.setNameResource("test.log");
        params.setFromDate(LocalDate.of(2023, 1, 1));
        params.setToDate(LocalDate.of(2023, 12, 31));
        generalInformation = new GeneralInformation(params);
    }

    @Test
    @DisplayName("Должен корректно формировать отчет общей информации")
    public void givenResult_whenGenerateGeneralInformationReport_thenAssertReportContent() {
        // Given
        LogAnalysisResult result = new LogAnalysisResult(
            Set.of("192.168.1.1", "192.168.1.2"), 200,
            Map.of("/resource", 100L), LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 12, 31), Map.of(200, 150L),
            500, 950
        );

        // When
        String report = generalInformation.generateReport(result);

        // Then
        assertTrue(report.contains("#### Общая информация"), "Отчет должен содержать заголовок 'Общая информация'");
        assertTrue(report.contains("|\nИсточник |test.log |"), "Отчет должен содержать имя ресурса");
        assertTrue(report.contains("|\nНачальная дата | 2023-01-01 |"), "Отчет должен содержать начальную дату");
        assertTrue(report.contains("|\nКонечная дата | 2023-12-31 |"), "Отчет должен содержать конечную дату");
        assertTrue(report.contains("| Количество запросов | 200 |"), "Отчет должен содержать количество запросов");
        assertTrue(report.contains("| Средний размер ответа | 500.0b |"), "Отчет должен содержать средний размер ответа");
        assertTrue(report.contains("| 95p размера ответа | 950.0b |"), "Отчет должен содержать 95-й перцентиль размера ответа");
    }
}
