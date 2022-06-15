package ru.job4j.serialization.json;

public class Address {
    private final String street;
    private final int houseNumber;

    public Address(String street, int houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{"
                + "street='" + street + '\''
                + ", houseNumber=" + houseNumber
                + '}';
    }
}
