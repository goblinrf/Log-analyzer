package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import backend.academy.service.GeneratorReport;


public class RequestedResources implements GeneratorReport {
    @Override
    public String generateReport(LogAnalysisResult result) {
        StringBuilder report = new StringBuilder();
        report.append("#### Запрашиваемые ресурсы\n\n")
            .append("| Ресурс | Количество  |\n")
            .append("|---------|----------|\n");
            for (var resource:result.resourceCounts().keySet()) {
                 report.append(resource).append(" | ").append(result.resourceCounts().get(resource)).append(" |\n");
        }

        return report.toString();
    }
}
