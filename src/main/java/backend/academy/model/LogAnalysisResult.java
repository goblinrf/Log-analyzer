package backend.academy.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ParameterNumber")
public record LogAnalysisResult(Set<String> uniqueIPs, long requestCount, Map<String, Long> resourceCounts,
                                LocalDate firstRequestTime, LocalDate lastRequestTime, Map<Integer, Long> statusCodes,
                                double averageResponseSize, double percentile95) {

    public LogAnalysisResult(
        Set<String> uniqueIPs,
        long requestCount,
        Map<String, Long> resourceCounts,
        LocalDate firstRequestTime,
        LocalDate lastRequestTime,
        Map<Integer, Long> statusCodes,
        double averageResponseSize,
        double percentile95
    ) {
        this.uniqueIPs = uniqueIPs;
        this.requestCount = requestCount;
        this.resourceCounts = resourceCounts;
        this.firstRequestTime = firstRequestTime;
        this.lastRequestTime = lastRequestTime;
        this.statusCodes = statusCodes;
        this.averageResponseSize = averageResponseSize;
        this.percentile95 = percentile95;
    }
}
