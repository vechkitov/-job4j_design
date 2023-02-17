package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.GsonCalendarTypeAdapter;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

class ReportJsonTest {

    @Test
    void whenGenerate() {
        Calendar now = Calendar.getInstance();
        Employee ivan = new Employee("Ivan", now, now, 100d);
        Employee petr = new Employee("Petr", now, now, 200d);
        MemStore store = new MemStore();
        store.add(ivan);
        store.add(petr);
        String dateStr = "01.01.2000";
        final String expected = "[{\"name\":\"" + ivan.getName() + "\","
                + "\"hired\":\"" + dateStr + "\","
                + "\"fired\":\"" + dateStr + "\","
                + "\"salary\":" + 100.0 + "},"
                + "{\"name\":\"" + petr.getName() + "\","
                + "\"hired\":\"" + dateStr + "\","
                + "\"fired\":\"" + dateStr + "\","
                + "\"salary\":" + 200.0 + "}]";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GregorianCalendar.class, new GsonCalendarTypeAdapter(calendar -> dateStr))
                .create();
        assertThat(new ReportJson(store, gson).generate(em -> true))
                .isEqualTo(expected);
    }
}
