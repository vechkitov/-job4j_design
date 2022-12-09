package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.CurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.function.Predicate;

public class ReportAccounting implements Report {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Store store;
    private final DateTimeParser<Calendar> parser;
    private final Currency sourceCurrency;
    private final Currency targetCurrency;
    private final CurrencyConverter converter;

    public ReportAccounting(Store store,
                            DateTimeParser<Calendar> parser,
                            Currency sourceCurrency,
                            Currency targetCurrency,
                            CurrencyConverter converter) {
        this.store = store;
        this.parser = parser;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.converter = converter;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder("Name; Hired; Fired; Salary;").append(LINE_SEPARATOR);
        for (Employee employee : store.findBy(filter)) {
            text.append(employee.getName()).append(" ")
                    .append(parser.parse(employee.getHired())).append(" ")
                    .append(parser.parse(employee.getFired())).append(" ")
                    .append(converter.convert(sourceCurrency, employee.getSalary(), targetCurrency))
                    .append(LINE_SEPARATOR);
        }
        return text.toString();
    }
}
