package backend.academy.service.impl.filters;

import backend.academy.model.LogRecord;
import backend.academy.service.LogFilter;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DateTimeFilterFromTo implements LogFilter {
    private final LocalDate from;
    private final LocalDate to;

    public DateTimeFilterFromTo(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean apply(LogRecord records) {
        LocalDate timestamp = records.timestamp();
        boolean afterFrom = from == null || !timestamp.isBefore(ChronoLocalDate.from(from));
        boolean beforeTo = to == null || !timestamp.isAfter(ChronoLocalDate.from(to));
        return afterFrom && beforeTo;
    }
}
