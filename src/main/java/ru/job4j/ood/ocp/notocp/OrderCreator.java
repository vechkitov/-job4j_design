package ru.job4j.ood.ocp.notocp;

/**
 * Нарушение OCP: если появится еще один вид заказа (например, на доставку), придется
 * изменять уже существующий код: добавлять метод выдачи этого нового вида заказа.
 */
public class OrderCreator {

    public void issuePickupOrder(PickupOrder order) {
        System.out.println("Заказ для самовывоза выдан");
    }

    public static class PickupOrder {
    }
}
