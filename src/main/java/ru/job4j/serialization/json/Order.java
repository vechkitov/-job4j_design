package ru.job4j.serialization.json;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    public boolean isDelivered() {
        return isDelivered;
    }

    public double getDeliveryCast() {
        return deliveryCast;
    }

    public String getNote() {
        return note;
    }

    public Address getAddress() {
        return address;
    }

    public String[] getCouriers() {
        return couriers;
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
        var jsonAddress = new JSONObject("{\n"
                + "\t\"street\":\"Лизюкова\",\n"
                + "\t\"houseNumber\":13\n"
                + "}");
        JSONArray jsonCouriers = new JSONArray(List.of("Мышкин", "Собакевич"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isDelivered", order.isDelivered());
        jsonObject.put("deliveryCast", order.getDeliveryCast());
        jsonObject.put("note", order.getNote());
        jsonObject.put("address", jsonAddress);
        jsonObject.put("couriers", jsonCouriers);
        System.out.println(jsonObject);
        System.out.println(new JSONObject(order));
    }
}
