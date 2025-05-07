package backend.academy.logic.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ModifiedControlVariable")
public class ParseParams {

    private LocalDate fromDate = null;
    private LocalDate toDate = null;
    private String path = null;
    private String format = "markdown";
    private String nameResource;
    private String field;
    private Map<String, List<String>> filterByField = new HashMap<>();

    public void parseInput(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--path":
                    path = args[++i];
                    nameResource = path;
                    break;
                case "--from":
                    fromDate = parseDate(args[++i]);
                    break;
                case "--to":
                    toDate = parseDate(args[++i]);
                    break;
                case "--filter-field":
                    field = args[++i];
                    if (!filterByField.containsKey(field)) {
                        filterByField.put(field, new ArrayList<>());
                    }
                    break;
                case "--filter-value":
                    String value = args[++i];
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    filterByField.get(field).add(value);
                    break;
                case "--format":
                    format = args[++i];
                    break;
                case "analyzer":
                    break;
                default:
                    throw new IllegalArgumentException("Неверный аргумент: " + args[i]);
            }
        }

        if (path == null) {
            throw new IllegalArgumentException("Параметр --path обязателен.");
        }
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getPath() {
        return path;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Map<String, List<String>> getFilterByField() {
        return filterByField;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getFormat() {
        return format;
    }

    public void setNameResource(String nameResource) {
        this.nameResource = nameResource;
    }

    public String getNameResource() {
        return nameResource;
    }

    private static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            return null;
        }
    }
}
