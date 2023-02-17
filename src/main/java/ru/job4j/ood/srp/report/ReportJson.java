package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.function.Predicate;

public class ReportJson implements Report {
    private final Store store;
    private final Gson gson;

    public ReportJson(Store store, Gson gson) {
        this.store = store;
        this.gson = gson;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        return gson.toJson(store.findBy(filter));
    }
}
