package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.CurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.srp.report.ReportAccounting.LINE_SEPARATOR;

class ReportAccountingTest {

    @Test
    void whenGenerate() {
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100d);
        MemStore store = new MemStore();
        store.add(worker);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        CurrencyConverter converter = (source, sourceValue, target) -> 3.0;
        String expected = new StringBuilder("Name; Hired; Fired; Salary;").append(LINE_SEPARATOR)
                .append(worker.getName()).append(" ")
                .append(parser.parse(worker.getHired())).append(" ")
                .append(parser.parse(worker.getFired())).append(" ")
                .append(3.0).append(LINE_SEPARATOR)
                .toString();
        assertThat(new ReportAccounting(store, parser, Currency.EUR, converter)
                .generate(em -> true))
                .isEqualTo(expected);
    }
}
