package ru.job4j.ood.lsp.notlsp;

import java.time.LocalDate;

public class Order {

    protected LocalDate created;
    protected double totalSum;
    protected double discountSum;

    public Order(LocalDate created) {
        this.created = created;
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public double getRevenueSum() {
        if (totalSum < discountSum) {
            throw new IllegalArgumentException("Общая сумма заказа не может быть меньше суммы скидки");
        }
        return totalSum - discountSum;
    }

    public void setTotalSum(double totalSum) {
        if (totalSum < 0) {
            throw new IllegalArgumentException("Сумма заказа не может быть меньше 0");
        }
        this.totalSum = totalSum;
    }
}
