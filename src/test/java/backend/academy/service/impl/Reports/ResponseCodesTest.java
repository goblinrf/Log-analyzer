package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import backend.academy.model.HttpStatusMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseCodesTest {

    private ResponseCodes responseCodes;

    @BeforeEach
    void setup() {
        responseCodes = new ResponseCodes();
    }

    @Test
    @DisplayName("Должен корректно формировать отчет по кодам ответа")
    void givenResult_whenGenerateResponseCodesReport_thenAssertReportContent() {
        // Given
        LogAnalysisResult result = new LogAnalysisResult(
            Set.of(), 0, null, null, null,
            Map.of(200, 150L, 404, 10L), 0, 0
        );

        // When
        String report = responseCodes.generateReport(result);

        // Then
        assertTrue(report.contains("#### Коды ответа"), "Отчет должен содержать заголовок 'Коды ответа'");
        assertTrue(report.contains("|\n200 | " + HttpStatusMapper.getStatusDescription(200) + " | 150 |"),
            "Отчет должен содержать статус-код 200 с соответствующим количеством");
        assertTrue(report.contains("|\n404 | " + HttpStatusMapper.getStatusDescription(404) + " | 10 |"),
            "Отчет должен содержать статус-код 404 с соответствующим количеством");
    }
}
