package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.srp.report.ReportProgramming.LINE_SEPARATOR;

class ReportProgrammingTest {

    @Test
    void whenGenerate() {
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 1d);
        Store store = new MemStore();
        store.add(worker);
        DateTimeParser<Calendar> parser = calendar -> "01.01.2000";
        String expected = new StringBuilder("Name;Hired;Fired;Salary").append(LINE_SEPARATOR)
                .append(worker.getName()).append(";")
                .append("01.01.2000;")
                .append("01.01.2000;")
                .append(1d).append(LINE_SEPARATOR)
                .toString();
        assertThat(new ReportProgramming(store, parser).generate(em -> true))
                .isEqualTo(expected);
    }
}
