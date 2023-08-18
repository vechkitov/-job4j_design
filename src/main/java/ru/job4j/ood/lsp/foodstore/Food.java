package ru.job4j.ood.lsp.foodstore;


import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class Food {

    public static final long SHELF_LIFE_PERCENT_25 = 25L;
    public static final long SHELF_LIFE_PERCENT_75 = 75L;
    public static final long SHELF_LIFE_PERCENT_100 = 100L;

    private final String name;
    private final LocalDateTime createDate;
    private final LocalDateTime expiryDate;
    private final double discount;
    private double price;

    public Food(String name, LocalDateTime createDate, LocalDateTime expiryDate, double discount, double price) {
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
        this.discount = discount;
        this.price = price;
    }

    /**
     * Возвращает израсходованный срок годности.
     *
     * @param date дата, относительно которой рассчитывается израсходованный срок годности
     * @return остаток срока годности, %. Всегда округляется в меньшую сторону до целого числа.
     */
    public long getPercentOfUsedShelfLife(LocalDateTime date) {
        long shelfLifeUsed = DAYS.between(createDate, date);
        long shelfLifeTotal = DAYS.between(createDate, expiryDate);
        return shelfLifeUsed * 100 / shelfLifeTotal;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return name.equals(food.name)
                && createDate.equals(food.createDate)
                && expiryDate.equals(food.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, createDate, expiryDate);
    }

    @Override
    public String toString() {
        return "Food{"
                + "name='" + name + '\''
                + ", createDate=" + createDate
                + ", expiryDate=" + expiryDate
                + ", discount=" + discount
                + ", price=" + price
                + '}';
    }
}
