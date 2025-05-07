package backend.academy.service;

import backend.academy.model.LogRecord;

public interface LogFilter {
    boolean apply(LogRecord records);
}
