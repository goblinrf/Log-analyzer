package backend.academy.service.impl.filters;

import backend.academy.model.LogRecord;
import backend.academy.service.LogFilter;

public class FilterMethod implements LogFilter {
    private String value;

    public FilterMethod(String value) {
        this.value = value;
    }

    @Override
    public boolean apply(LogRecord records) {
        return value != null && value.equals(records.request());
    }
}
