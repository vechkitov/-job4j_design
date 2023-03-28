package ru.job4j.ood.lsp.foodstore;

import java.time.LocalDateTime;

public class Warehouse extends AbstractStore {

    @Override
    public boolean isShelfLifeCorrect(Food food, LocalDateTime date) {
        return food.getPercentOfUsedShelfLife(date) < Food.SHELF_LIFE_PERCENT_25;
    }
}
