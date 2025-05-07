package backend.academy.logic;

import backend.academy.factory.AppendReportFactory;
import backend.academy.factory.FilterFactory;
import backend.academy.factory.LogReportFactory;
import backend.academy.factory.ReadResourceFactory;
import backend.academy.logic.parser.LogParser;
import backend.academy.logic.parser.ParseParams;
import backend.academy.service.impl.reader.LogFileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class LogController {
    private final LogAnalyzer analyzer = new LogAnalyzer();
    private final LogFileReader logFileReader = new LogFileReader();
    private static final String[] REPORT_TYPES = {"general", "resource", "codes", "ip", "dates"};

    public void start(ParseParams parseParams) throws IOException {
        FilterFactory filterFactory = new FilterFactory(analyzer, parseParams);
        filterFactory.applyFilter();

        if (isURL(parseParams.getPath())) {
            try (Stream<String> logStream = ReadResourceFactory.createReadResource("url")
                .readSource(parseParams.getPath())) {
                processLogStream(parseParams, logStream);
            }
        } else {
            try (Stream<String> fileStream = ReadResourceFactory.createReadResource("file")
                .readSource(parseParams.getPath())) {
                processLogStream(parseParams, fileStream);
            }
        }
    }

    private void processLogStream(ParseParams parseParams, Stream<String> logStream) throws IOException {
        AppendReportFactory appendReportFactory = new AppendReportFactory();
        LogReportFactory logReportFactory = new LogReportFactory();
        LogParser parser = new LogParser();
        logStream.forEach(line -> {
            analyzer.processRecord(parser.parseLine(line));
        });

        StringBuilder report = appendReportFactory.appendReport(
            REPORT_TYPES,
            analyzer.generateFinalReport(),
            parseParams
        );
        logReportFactory.fileExtensionAndSaveReport(report, parseParams.getFormat());
    }

    private boolean isURL(String input) {
        return input.startsWith("http://") || input.startsWith("https://");
    }
}
