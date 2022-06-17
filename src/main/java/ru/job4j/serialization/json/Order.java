package ru.job4j.serialization.json;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

@XmlRootElement
public class Order {

    @XmlAttribute
    private boolean isDelivered;

    @XmlAttribute
    private double deliveryCast;

    @XmlAttribute
    private String note;

    @XmlElement
    private Address address;

    @XmlElementWrapper
    @XmlElement(name = "courier")
    private String[] couriers;

    public Order() {
    }

    public Order(boolean isDelivered, double deliveryCast, String note, Address address, String[] couriers) {
        this.isDelivered = isDelivered;
        this.deliveryCast = deliveryCast;
        this.note = note;
        this.address = address;
        this.couriers = couriers;
    }

    @Override
    public String toString() {
        return "Order{"
                + "isDelivered=" + isDelivered
                + ", deliveryCast=" + deliveryCast
                + ", note='" + note + '\''
                + ", address=" + address
                + ", couriers=" + Arrays.toString(couriers)
                + '}';
    }

    public static void main(String[] args) throws JAXBException, IOException {
        final Order order = new Order(false, 300d, "после обеда",
                new Address("Лизюкова", 13),
                new String[]{"Мышкин", "Собакевич"});
        JAXBContext context = JAXBContext.newInstance(Order.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml;
        try (var writer = new StringWriter()) {
            marshaller.marshal(order, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (var reader = new StringReader(xml)) {
            Order result = (Order) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }
    }
}
