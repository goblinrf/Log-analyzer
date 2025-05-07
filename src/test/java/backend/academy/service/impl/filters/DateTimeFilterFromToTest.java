package backend.academy.service.impl.filters;

import backend.academy.model.LogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeFilterFromToTest {
    private DateTimeFilterFromTo filter;
    private LogRecord record;
    private final LocalDate filterFrom = LocalDate.of(2023, 1, 1);
    private final LocalDate filterTo = LocalDate.of(2023, 12, 31);

    @BeforeEach
    public void setUp() {

        record = new LogRecord("127.0.0.1", "user", LocalDate.of(2023, 6, 15), "GET", "/resource", 200, 1024, null, "User-Agent");
    }

    @Test
    @DisplayName("Должен пропускать запись в пределах диапазона дат")
    public void givenDateWithinRange_whenApplyFilter_thenPasses() {
        // Given
        filter = new DateTimeFilterFromTo(filterFrom, filterTo);

        // When
        boolean result = filter.apply(record);

        // Then
        assertTrue(result, "Фильтр должен пропустить запись, если дата записи в пределах диапазона.");
    }

    @Test
    @DisplayName("Не должен пропускать запись, если дата раньше начала диапазона")
    public void givenDateBeforeRange_whenApplyFilter_thenFails() {
        // Given
        filter = new DateTimeFilterFromTo(filterFrom, filterTo);

        // When
        record = new LogRecord("127.0.0.1", "user", LocalDate.of(2022, 12, 31), "GET", "/resource", 200, 1024, null, "User-Agent");
        boolean result = filter.apply(record);

        // Then
        assertFalse(result, "Фильтр не должен пропустить запись, если дата записи раньше начала диапазона.");
    }

    @Test
    @DisplayName("Не должен пропускать запись, если дата позже конца диапазона")
    public void givenDateAfterRange_whenApplyFilter_thenFails() {
        // Given
        filter = new DateTimeFilterFromTo(filterFrom, filterTo);

        // When
        record = new LogRecord("127.0.0.1", "user", LocalDate.of(2024, 1, 1), "GET", "/resource", 200, 1024, null, "User-Agent");
        boolean result = filter.apply(record);

        // Then
        assertFalse(result, "Фильтр не должен пропустить запись, если дата записи позже конца диапазона.");
    }

    @Test
    @DisplayName("Должен пропускать запись, если начало диапазона null")
    public void givenNullFromDate_whenApplyFilter_thenPasses() {
        // Given
        filter = new DateTimeFilterFromTo(null, filterTo);

        // When
        boolean result = filter.apply(record);

        // Then
        assertTrue(result, "Фильтр должен пропустить запись, если начало диапазона null.");
    }

    @Test
    @DisplayName("Должен пропускать запись, если конец диапазона null")
    public void givenNullToDate_whenApplyFilter_thenPasses() {
        // Given
        filter = new DateTimeFilterFromTo(filterFrom, null);

        // When
        boolean result = filter.apply(record);

        // Then
        assertTrue(result, "Фильтр должен пропустить запись, если конец диапазона null.");
    }
}
