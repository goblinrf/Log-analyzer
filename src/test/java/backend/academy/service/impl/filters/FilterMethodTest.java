package backend.academy.service.impl.filters;

import backend.academy.model.LogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterMethodTest {
    private FilterMethod filter;
    private LogRecord matchingRecord;
    private LogRecord nonMatchingRecord;
    private final String filterValue = "GET /home";

    @BeforeEach
    public void setUp() {
        // Given
        matchingRecord = new LogRecord("127.0.0.1", "user", null, filterValue, "/home", 200, 1024, null, "User-Agent");
        nonMatchingRecord = new LogRecord("127.0.0.1", "user", null, "POST /login", "/login", 200, 512, null, "User-Agent");

        filter = new FilterMethod(filterValue);
    }

    @Test
    @DisplayName("Должен пропускать запись, если запрос совпадает с фильтром")
    public void givenMatchingRequest_whenApplyFilter_thenPasses() {
        // When
        boolean result = filter.apply(matchingRecord);

        // Then
        assertTrue(result, "Фильтр должен пропускать запись, если запрос совпадает с фильтром.");
    }

    @Test
    @DisplayName("Не должен пропускать запись, если запрос не совпадает с фильтром")
    public void givenNonMatchingRequest_whenApplyFilter_thenFails() {
        // When
        boolean result = filter.apply(nonMatchingRecord);

        // Then
        assertFalse(result, "Фильтр не должен пропускать запись, если запрос не совпадает с фильтром.");
    }

    @Test
    @DisplayName("Не должен пропускать запись, если запрос в записи null")
    public void givenNullRequest_whenApplyFilter_thenFails() {
        // Given
        LogRecord nullRequestRecord = new LogRecord("127.0.0.1", "user", null, null, "/null", 200, 1024, null, "User-Agent");

        // When
        boolean result = filter.apply(nullRequestRecord);

        // Then
        assertFalse(result, "Фильтр не должен пропускать запись, если запрос равен null.");
    }

    @Test
    @DisplayName("Не должен пропускать запись, если значение фильтра null")
    public void givenNullFilterValue_whenApplyFilter_thenFails() {
        // Given: фильтр с null-значением
        filter = new FilterMethod(null);

        // When
        boolean result = filter.apply(matchingRecord);

        // Then
        assertFalse(result, "Фильтр не должен пропускать запись, если значение фильтра null.");
    }
}
