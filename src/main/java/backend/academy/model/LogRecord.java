package backend.academy.model;

import java.time.LocalDate;

@SuppressWarnings({"RecordComponentNumber", "ParameterNumber"})
public record LogRecord(String ip, String user, LocalDate timestamp, String request, String resource, int statusCode,
                        int responseSize, String referer, String userAgent) {

    public LogRecord(
        String ip, String user, LocalDate timestamp, String request, String resource, int statusCode,
        int responseSize, String referer, String userAgent
    ) {
        this.ip = ip;
        this.user = user;
        this.timestamp = timestamp;
        this.request = request;
        this.resource = resource;
        this.statusCode = statusCode;
        this.responseSize = responseSize;
        this.referer = referer;
        this.userAgent = userAgent;
    }
}
