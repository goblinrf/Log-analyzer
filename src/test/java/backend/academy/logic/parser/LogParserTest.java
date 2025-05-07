package backend.academy.logic.parser;

import backend.academy.model.LogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

class LogParserTest {
    private LogParser logParser;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH);

    @BeforeEach
    public void setUp() {
        logParser = new LogParser();
    }

    @Test
    @DisplayName("Парсинг: корректная строка лога должна быть успешно распознана")
    public void givenValidLogLine_whenParseLine_thenLogRecordParsedCorrectly() {
        // Given
        String logLine = "127.0.0.1 - user1 [10/Oct/2023:13:55:36 +0000] \"GET /home HTTP/1.1\" 200 1234 \"http://example.com\" \"Mozilla/5.0\"";

        // When
        LogRecord logRecord = logParser.parseLine(logLine);

        // Then
        assertEquals("127.0.0.1", logRecord.ip(), "Должен корректно распознать IP.");
        assertEquals("user1", logRecord.user(), "Должен корректно распознать имя пользователя.");
        assertEquals(LocalDate.parse("10/Oct/2023", DATE_FORMAT), logRecord.timestamp(), "Должен корректно распознать дату.");
        assertEquals("GET", logRecord.request(), "Должен корректно распознать тип запроса.");
        assertEquals("/home", logRecord.resource(), "Должен корректно распознать ресурс.");
        assertEquals(200, logRecord.statusCode(), "Должен корректно распознать статус код.");
        assertEquals(1234, logRecord.responseSize(), "Должен корректно распознать размер ответа.");
        assertEquals("http://example.com", logRecord.referer(), "Должен корректно распознать реферер.");
        assertEquals("Mozilla/5.0", logRecord.userAgent(), "Должен корректно распознать User-Agent.");
    }

    @Test
    @DisplayName("Парсинг: строка без имени пользователя должна быть корректно распознана с user = null")
    public void givenLogLineWithoutUser_whenParseLine_thenUserIsNull() {
        // Given
        String logLine = "127.0.0.1 - - [10/Oct/2023:13:55:36 +0000] \"GET /home HTTP/1.1\" 200 1234 \"http://example.com\" \"Mozilla/5.0\"";

        // When
        LogRecord logRecord = logParser.parseLine(logLine);

        // Then
        assertNull(logRecord.user(), "Имя пользователя должно быть null.");
    }

    @Test
    @DisplayName("Парсинг: строка без реферера и User-Agent должна быть корректно распознана с null значениями")
    public void givenLogLineWithoutRefererAndUserAgent_whenParseLine_thenRefererAndUserAgentAreNull() {
        // Given
        String logLine = "127.0.0.1 - user1 [10/Oct/2023:13:55:36 +0000] \"GET /home HTTP/1.1\" 200 1234 \"\" \"\"";

        // When
        LogRecord logRecord = logParser.parseLine(logLine);

        // Then
        assertNull(logRecord.referer(), "Referer должен быть null.");
        assertNull(logRecord.userAgent(), "User-Agent должен быть null.");
    }

    @Test
    @DisplayName("Ошибка парсинга: некорректная строка должна вызвать исключение IllegalArgumentException")
    public void givenInvalidLogLine_whenParseLine_thenThrowsIllegalArgumentException() {
        // Given
        String invalidLogLine = "Некорректная строка лога";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> logParser.parseLine(invalidLogLine));
        assertEquals("Некорректный формат строки лога: Некорректная строка лога", exception.getMessage(), "Сообщение об ошибке должно соответствовать ожидаемому.");
    }

    @Test
    @DisplayName("Извлечение ресурса: должен корректно выделять ресурс из строки запроса")
    public void givenRequestLine_whenExtractResourcePath_thenResourcePathExtractedCorrectly() {
        // Given
        String request = "GET /test/path HTTP/1.1";

        // When
        String resource = logParser.extractResourcePath(request);

        // Then
        assertEquals("/test/path", resource, "Ресурс должен быть корректно выделен из строки запроса.");
    }
}
