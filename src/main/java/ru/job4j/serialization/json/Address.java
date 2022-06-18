package ru.job4j.serialization.json;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address {

    @XmlAttribute
    private String street;

    @XmlAttribute
    private int houseNumber;

    public Address() {
    }

    public Address(String street, int houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    @Override
    public String toString() {
        return "Address{"
                + "street='" + street + '\''
                + ", houseNumber=" + houseNumber
                + '}';
    }
}
