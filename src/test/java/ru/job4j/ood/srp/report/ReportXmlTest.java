package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.XmlCalendarTypeAdapter;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class ReportXmlTest {

    @Test
    void whenGenerate() throws JAXBException {
        Calendar now = Calendar.getInstance();
        Employee ivan = new Employee("Ivan", now, now, 100d);
        Employee petr = new Employee("Petr", now, now, 200d);
        MemStore store = new MemStore();
        store.add(ivan);
        store.add(petr);
        String dateStr = "01.01.2000";
        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<employees>\n"
                + "    <employee>\n"
                + "        <name>" + ivan.getName() + "</name>\n"
                + "        <hired>" + dateStr + "</hired>\n"
                + "        <fired>" + dateStr + "</fired>\n"
                + "        <salary>" + 100d + "</salary>\n"
                + "    </employee>\n"
                + "    <employee>\n"
                + "        <name>" + petr.getName() + "</name>\n"
                + "        <hired>" + dateStr + "</hired>\n"
                + "        <fired>" + dateStr + "</fired>\n"
                + "        <salary>" + 200d + "</salary>\n"
                + "    </employee>\n"
                + "</employees>\n";
        Marshaller marshaller = JAXBContext.newInstance(ReportXml.Employees.class)
                .createMarshaller();
        marshaller.setAdapter(XmlCalendarTypeAdapter.class, new XmlCalendarTypeAdapter(calendar -> dateStr));
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        assertThat(new ReportXml(store, marshaller).generate(em -> true))
                .isEqualTo(expected);
    }
}
