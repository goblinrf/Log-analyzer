package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestedResourcesTest {

    private RequestedResources requestedResources;

    @BeforeEach
    void setup() {
        requestedResources = new RequestedResources();
    }

    @Test
    @DisplayName("Должен корректно формировать отчет по запрашиваемым ресурсам")
    void givenResult_whenGenerateRequestedResourcesReport_thenAssertReportContent() {
        // Given
        LogAnalysisResult result = new LogAnalysisResult(
            Set.of(), 0, Map.of("/test", 10L, "/example", 20L),
            null, null, null, 0, 0
        );

        // When
        String report = requestedResources.generateReport(result);

        // Then
        assertTrue(report.contains("/test | 10 |"), "Отчет должен содержать ресурс '/test' с количеством запросов 10");
        assertTrue(report.contains("/example | 20 |"), "Отчет должен содержать ресурс '/example' с количеством запросов 20");
    }
}
