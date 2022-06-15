package ru.job4j.serialization.json;

import com.google.gson.Gson;

import java.util.Arrays;

public class Order {
    private final boolean isDelivered;
    private final double deliveryCast;
    private final String note;
    private final Address address;
    private final String[] couriers;

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

    public static void main(String[] args) {
        final Order order = new Order(false, 300d, "после обеда",
                new Address("Лизюкова", 13),
                new String[]{"Мышкин", "Собакевич"});
        final Gson gson = new Gson();
        System.out.println(gson.toJson(order));
        String orderJson = "{\n"
                + "\"isDelivered\" : true,\n"
                + "\"deliveryCast\" : 300.0,\n"
                + "\"note\" : \"после обеда\",\n"
                + "\"address\" : {\n"
                + "\t\"street\" : \"Лизюкова\",\n"
                + "\t\"houseNumber\" : 13\n"
                + "},\n"
                + "\"couriers\" : [\"Мышкин\", \"Собакевич\"]\n"
                + "}";
        Order orderMod = gson.fromJson(orderJson, Order.class);
        System.out.println(orderMod);
    }
}
