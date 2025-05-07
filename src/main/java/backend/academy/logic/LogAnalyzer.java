package backend.academy.logic;

import backend.academy.model.LogAnalysisResult;
import backend.academy.model.LogRecord;
import backend.academy.service.LogFilter;
import com.google.common.math.Quantiles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"ParameterAssignment", "MagicNumber"})
public class LogAnalyzer {
    private final List<LogFilter> filters = new ArrayList<>();
    private long requestCount;
    private long totalResponseSize;
    private final List<Integer> responseSizes = new ArrayList<>();
    private final Map<String, Long> resourceCounts = new HashMap<>();
    private final Map<Integer, Long> statusCodes = new HashMap<>();
    private LocalDate firstRequestTime;
    private LocalDate lastRequestTime;
    private final Set<String> uniqueIPs = new HashSet<>();

    public void addFilter(LogFilter filter) {
        filters.add(filter);
    }

    public boolean applyFilters(LogRecord logRecord) {
        for (LogFilter filter : filters) {
            if (!filter.apply(logRecord)) {
                return false;
            }
        }
        return true;
    }

    public void processRecord(LogRecord logRecord) {
        if (!applyFilters(logRecord)) {
            return;
        }

        requestCount++;
        totalResponseSize += logRecord.responseSize();
        responseSizes.add(logRecord.responseSize());
        uniqueIPs.add(logRecord.ip());
        findFirstAndLastRequestTime(logRecord.timestamp());
        resourceCounts.merge(logRecord.resource(), 1L, Long::sum);
        statusCodes.merge(logRecord.statusCode(), 1L, Long::sum);
    }

    public LogAnalysisResult generateFinalReport() {

        double averageResponseSize = (requestCount > 0) ? (double) totalResponseSize / requestCount : 0;

        double percentile95 = Quantiles.percentiles().index(95).compute(responseSizes);

        return new LogAnalysisResult(
            uniqueIPs,
            requestCount,
            resourceCounts,
            firstRequestTime,
            lastRequestTime,
            statusCodes,
            averageResponseSize,
            percentile95
        );
    }

    private void findFirstAndLastRequestTime(LocalDate timestamp) {
        if (firstRequestTime == null || timestamp.isBefore(firstRequestTime)) {
            firstRequestTime = timestamp;
        }
        if (lastRequestTime == null || timestamp.isAfter(lastRequestTime)) {
            lastRequestTime = timestamp;
        }
    }
}
