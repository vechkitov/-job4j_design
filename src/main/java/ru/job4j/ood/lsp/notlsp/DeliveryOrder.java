package ru.job4j.ood.lsp.notlsp;

import java.time.LocalDate;

public class DeliveryOrder extends Order {

    public DeliveryOrder(LocalDate created) {
        super(created);
    }

    /* 1) Нарушение LSP: усиление предусловий: в этом методе в базовом классе нет
        предусловий, а этом классе-потомке они появляются. Клиентский код, использующий базовый класс, не
        ожидает от этого метода каких-либо проверок. */
    @Override
    public void setDiscountSum(double discountSum) {
        if (discountSum < 0) {
            throw new IllegalArgumentException();
        }
        this.discountSum = discountSum;
    }

    /* 2) Нарушение LSP: ослабление постусловий: в этом методе в базовом классе выполняется проверка, а
    в этом классе-потомке ее нет. */
    @Override
    public double getRevenueSum() {
        return totalSum - discountSum;
    }

    /* 3) Нарушение LSP: нарушается инвариант базового класса: нет проверки как в базовом классе, возможно
    невалидное состояние */
    @Override
    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }
}
