package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ReportHr implements Report {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Store store;
    /* Т.к. в задании прямо сказано, что этот отчет должен "выводить сотрудников в порядке убывания зарплаты",
     * решил инициализировать Comparator прямо здесь. Если бы не был указан конкретный тип сортировки,
     * передавал бы Comparator в конструктор */
    private final Comparator<Employee> comparator = Comparator.comparingDouble(Employee::getSalary).reversed();

    public ReportHr(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder("Name; Salary;").append(LINE_SEPARATOR);
        List<Employee> employees = store.findBy(filter);
        employees.sort(comparator);
        for (Employee em : employees) {
            text.append(em.getName()).append(" ")
                    .append(em.getSalary()).append(LINE_SEPARATOR);
        }
        return text.toString();
    }
}
