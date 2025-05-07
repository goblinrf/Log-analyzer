package backend.academy.service.impl.Reports;

import backend.academy.model.LogAnalysisResult;
import backend.academy.service.GeneratorReport;
import static backend.academy.model.HttpStatusMapper.getStatusDescription;

@SuppressWarnings("MultipleStringLiterals")
public class ResponseCodes implements GeneratorReport {
    @Override
    public String generateReport(LogAnalysisResult result) {
        StringBuilder report = new StringBuilder();
        report.append("#### Коды ответа\n\n")
            .append("| Код |          Имя          | Количество |\n")
            .append("|:---:|:---------------------:|-----------:|\n");
        for (var code : result.statusCodes().keySet()) {
            report.append(code).append(" | ").append(getStatusDescription(code)).append(" | ")
                .append(result.statusCodes().get(code)).append(" |\n");
        }
        return report.toString();
    }
}
