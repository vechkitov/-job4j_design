package ru.job4j.ood.lsp.foodstore;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStore implements Store {

    private final Set<Food> foods = new HashSet<>();

    @Override
    public abstract boolean isShelfLifeCorrect(Food food, LocalDateTime date);

    @Override
    public void addFood(Food food) {
        foods.add(food);
    }

    @Override
    public Set<Food> getFoods() {
        return foods;
    }

    @Override
    public void clear() {
        foods.clear();
    }
}
