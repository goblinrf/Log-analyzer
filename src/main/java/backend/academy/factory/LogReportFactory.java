package backend.academy.factory;

import backend.academy.printer.LogReport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;

@SuppressFBWarnings("LSC_LITERAL_STRING_COMPARISON")
public class LogReportFactory {
    public void fileExtensionAndSaveReport(StringBuilder report, String extension) throws IOException {
        LogReport logReport = new LogReport();
        switch (extension.toLowerCase()) {
            case "markdown":
                logReport.saveReport(report, "src/main/Reports/report.md");
                break;
            case "adoc":
                logReport.saveReport(report, "src/main/Reports/report.adoc");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + extension.toLowerCase());
        }

    }
}
