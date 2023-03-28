package ru.job4j.ood.lsp.foodstore;

import java.time.LocalDateTime;

public class Shop extends AbstractStore {

    @Override
    public boolean isShelfLifeCorrect(Food food, LocalDateTime date) {
        long percent = food.getPercentOfUsedShelfLife(date);
        return percent >= Food.SHELF_LIFE_PERCENT_25
                && percent < Food.SHELF_LIFE_PERCENT_100;
    }
}
