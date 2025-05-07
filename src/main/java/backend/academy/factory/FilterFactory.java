package backend.academy.factory;

import backend.academy.logic.LogAnalyzer;
import backend.academy.logic.parser.ParseParams;
import backend.academy.service.impl.filters.DateTimeFilterFromTo;
import backend.academy.service.impl.filters.FilterMethod;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("LSC_LITERAL_STRING_COMPARISON")
public class FilterFactory {
    private LogAnalyzer analyzer;
    private ParseParams parseParams;

    public FilterFactory(LogAnalyzer analyzer, ParseParams parseParams) {
        this.analyzer = analyzer;
        this.parseParams = parseParams;
    }

    public void applyFilter() {
        if (parseParams.getFromDate() != null) {
            DateTimeFilterFromTo dateFilter = new DateTimeFilterFromTo(
                parseParams.getFromDate(),
                parseParams.getToDate()
            );
            analyzer.addFilter(dateFilter);
        }
        for (var key : parseParams.getFilterByField().keySet()) {
            for (var request : parseParams.getFilterByField().get(key)) {
                switch (key.toLowerCase()) {
                    case "method":
                        FilterMethod filterMethod = new FilterMethod(request);
                        analyzer.addFilter(filterMethod);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + key.toLowerCase());
                }
            }
        }
    }
}
