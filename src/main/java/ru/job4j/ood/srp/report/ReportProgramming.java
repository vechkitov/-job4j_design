package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.function.Predicate;

public class ReportProgramming implements Report {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Store store;
    private final DateTimeParser<Calendar> parser;

    public ReportProgramming(Store store, DateTimeParser<Calendar> parser) {
        this.store = store;
        this.parser = parser;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder("Name;Hired;Fired;Salary").append(LINE_SEPARATOR);
        for (Employee em : store.findBy(filter)) {
            text.append(em.getName()).append(";")
                    .append(parser.parse(em.getHired())).append(";")
                    .append(parser.parse(em.getFired())).append(";")
                    .append(em.getSalary()).append(LINE_SEPARATOR);
        }
        return text.toString();
    }
}
