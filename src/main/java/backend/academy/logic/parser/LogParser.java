package backend.academy.logic.parser;

import backend.academy.model.LogRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("MagicNumber")
public class LogParser {
    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(\\S+) - (\\S+) \\[(.+?)] \"(\\S+\\s+\\S+\\s+\\S*)\" (\\d{3}) (\\d+) \"(.*?)\" \"(.*?)\"$"
    );

    private static final Pattern REQUEST_PATTERN = Pattern.compile("^(\\S+)\\s+(\\S+)\\s+HTTP/\\d\\.\\d$");

    public LogRecord parseLine(String line) {
        Matcher matcher = LOG_PATTERN.matcher(line);

        if (matcher.matches()) {
            String ip = matcher.group(1);
            String user = "-".equals(matcher.group(2)) ? null : matcher.group(2);
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(3), DATE_FORMAT);
            LocalDate timestamp = dateTime.toLocalDate();

            String request = matcher.group(4).split(" ")[0];
            String resource = extractResourcePath(matcher.group(4));

            int statusCode = Integer.parseInt(matcher.group(5));
            int responseSize = Integer.parseInt(matcher.group(6));
            String referer = matcher.group(7).isEmpty() ? null : matcher.group(7);
            String userAgent = matcher.group(8).isEmpty() ? null : matcher.group(8);

            return new LogRecord(ip, user, timestamp, request, resource, statusCode, responseSize, referer, userAgent);
        } else {
            throw new IllegalArgumentException("Некорректный формат строки лога: " + line);
        }
    }

    protected String extractResourcePath(String request) {
        Matcher matcher = REQUEST_PATTERN.matcher(request);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return request;
    }

}
