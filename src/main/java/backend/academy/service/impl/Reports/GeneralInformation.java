package backend.academy.service.impl.Reports;

import backend.academy.logic.parser.ParseParams;
import backend.academy.model.LogAnalysisResult;
import backend.academy.service.GeneratorReport;

@SuppressWarnings("MultipleStringLiterals")
public class GeneralInformation implements GeneratorReport {
    private final ParseParams parseParams;

    public GeneralInformation(ParseParams parseParams) {
        this.parseParams = parseParams;
    }

    @Override
    public String generateReport(LogAnalysisResult result) {
        StringBuilder report = new StringBuilder();

        report.append("#### Общая информация\n\n")
            .append("| Метрика | Значение |\n")
            .append("|---------|----------|\n")
            .append("Источник |").append(parseParams.getNameResource()).append(" |\n")
            .append("Начальная дата | ").append(parseParams.getFromDate() == null ? "-" : parseParams.getFromDate())
            .append(" |\n")
            .append("Конечная дата | ").append(parseParams.getToDate() == null ? "-" : parseParams.getToDate())
            .append(" |\n")
            .append("| Количество запросов | ").append(result.requestCount()).append(" |\n")
            .append("| Средний размер ответа | ").append(result.averageResponseSize()).append("b |\n")
            .append("| 95p размера ответа | ").append(result.percentile95()).append("b |\n");
        return report.toString();
    }

}
