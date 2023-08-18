package ru.job4j.ood.lsp.foodstore;

import java.time.LocalDateTime;
import java.util.Set;

public interface Store {

    /**
     * Помещает продукт в хранилище
     *
     * @param food
     */
    void addFood(Food food);

    /**
     * Возвращает набор продуктов, находящихся в хранилище
     *
     * @return набор продуктов
     */
    Set<Food> getFoods();

    /**
     * Проверяет соответствие израсходованного срока годности продукта условиям хранилища.
     *
     * @param food продукт
     * @param date дата, относительно которой рассчитывается израсходованный срок годности продукта
     * @return true - срока годности продукта соответствует условиям хранилища; false - не соответствует
     */
    boolean isShelfLifeCorrect(Food food, LocalDateTime date);

    /**
     * Удаляет все продукты из хранилища
     */
    void clear();
}
