package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import backend.academy.service.GeneratorReport;

public class UniqIpReport implements GeneratorReport {
    @Override
    public String generateReport(LogAnalysisResult result) {
        return "#### Уникальные ip\n\n"
            + "Общее количество уникальных IP-адресов: |"
            + result.uniqueIPs().size()
            + "Общее количество запросов: |" + result.requestCount() + "\n";
    }
}
