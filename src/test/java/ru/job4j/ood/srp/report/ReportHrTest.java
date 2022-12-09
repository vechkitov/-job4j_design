package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.srp.report.ReportHr.LINE_SEPARATOR;

class ReportHrTest {

    @Test
    void whenGenerate() {
        Calendar now = Calendar.getInstance();
        Employee worker1 = new Employee("Ivan", now, now, 1d);
        Employee worker2 = new Employee("Petr", now, now, 3d);
        Employee worker3 = new Employee("Aron", now, now, 2d);
        Store store = new MemStore();
        store.add(worker1);
        store.add(worker2);
        store.add(worker3);
        String expected = new StringBuilder("Name; Salary;").append(LINE_SEPARATOR)
                .append(worker2.getName()).append(" ")
                .append(worker2.getSalary()).append(LINE_SEPARATOR)
                .append(worker3.getName()).append(" ")
                .append(worker3.getSalary()).append(LINE_SEPARATOR)
                .append(worker1.getName()).append(" ")
                .append(worker1.getSalary()).append(LINE_SEPARATOR)
                .toString();
        assertThat(new ReportHr(store).generate(em -> true))
                .isEqualTo(expected);
    }
}
