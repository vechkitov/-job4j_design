package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Predicate;

public class ReportXml implements Report {
    private final Store store;
    private final Marshaller marshaller;

    public ReportXml(Store store, Marshaller marshaller) {
        this.store = store;
        this.marshaller = marshaller;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        Writer writer = new StringWriter();
        try {
            marshaller.marshal(new Employees(store.findBy(filter)), writer);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    @XmlRootElement
    public static class Employees {

        private List<Employee> employees;

        public Employees() {
        }

        public Employees(List<Employee> employees) {
            this.employees = employees;
        }

        @XmlElement(name = "employee")
        public List<Employee> getEmployees() {
            return employees;
        }
    }
}
