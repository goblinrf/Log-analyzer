package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import backend.academy.service.GeneratorReport;

public class FirstLastTimeReport implements GeneratorReport {
    @Override
    public String generateReport(LogAnalysisResult result) {
        StringBuilder report = new StringBuilder();
        report.append("#### Время первого и последнего запроса\n\n")
            .append("| Первый запрос | Последний запрос |\n")
            .append("|---------|----------|\n")
            .append(result.firstRequestTime()).append(" | ").append(result.lastRequestTime()).append(" |\n");
        return report.toString();
    }
}
