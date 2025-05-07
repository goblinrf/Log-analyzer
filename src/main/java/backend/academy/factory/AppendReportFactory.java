package backend.academy.factory;

import backend.academy.logic.parser.ParseParams;
import backend.academy.model.LogAnalysisResult;
import backend.academy.service.impl.Reports.FirstLastTimeReport;
import backend.academy.service.impl.Reports.GeneralInformation;
import backend.academy.service.impl.Reports.RequestedResources;
import backend.academy.service.impl.Reports.ResponseCodes;
import backend.academy.service.impl.Reports.UniqIpReport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("LSC_LITERAL_STRING_COMPARISON")
public class AppendReportFactory {

    public StringBuilder appendReport(String[] reportsName, LogAnalysisResult result, ParseParams parseParams) {
        StringBuilder report = new StringBuilder();

        for (String name : reportsName) {
            switch (name.toLowerCase()) {
                case "general":
                    GeneralInformation generalInformation = new GeneralInformation(parseParams);
                    report.append(generalInformation.generateReport(result));
                    break;
                case "resource":
                    RequestedResources requestedResources = new RequestedResources();
                    report.append(requestedResources.generateReport(result));
                    break;
                case "codes":
                    ResponseCodes responseCodes = new ResponseCodes();
                    report.append(responseCodes.generateReport(result));
                    break;
                case "ip":
                    UniqIpReport uniqIpReport = new UniqIpReport();
                    report.append(uniqIpReport.generateReport(result));
                    break;
                case "dates":
                    FirstLastTimeReport firstLastTimeReport = new FirstLastTimeReport();
                    report.append(firstLastTimeReport.generateReport(result));
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
            }
        }

        return report;
    }
}
