package backend.academy.service;

import backend.academy.model.LogAnalysisResult;

public interface GeneratorReport {
    String generateReport(LogAnalysisResult result);
}
