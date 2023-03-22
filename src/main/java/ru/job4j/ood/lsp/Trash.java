package ru.job4j.ood.lsp;

import java.time.LocalDateTime;

public class Trash extends AbstractStore {

    @Override
    public boolean isShelfLifeCorrect(Food food, LocalDateTime date) {
        return food.getPercentOfUsedShelfLife(date) >= Food.SHELF_LIFE_PERCENT_100;
    }
}
