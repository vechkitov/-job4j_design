package ru.job4j.ood.lsp.foodstore;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Сервис распределения продуктов по хранилищам в зависимости от срока годности.
 */
public class ControlQuality {

    private final Set<Store> stores;

    public ControlQuality(Set<Store> stores) {
        this.stores = stores;
    }

    /**
     * Помещает продукт в одно из хранилищ в соответствии со значением израсходованного срока годности.
     * Перед помещением в хранилище применяет скидку к цене продукта.
     *
     * @param food продукт
     * @param date дата, относительно которой рассчитывается израсходованный срок годности продукта
     */
    public void allocateFoodToStore(Food food, LocalDateTime date) {
        applyDiscount(food, date);
        for (Store store : stores) {
            if (store.isShelfLifeCorrect(food, date)) {
                store.addFood(food);
                break;
            }
        }
    }

    /**
     * Устанавливает новую цену на продукт в соответствие со значением скидки
     *
     * @param food продукт
     * @param date дата, относительно которой рассчитывается израсходованный срок годности продукта
     */
    private void applyDiscount(Food food, LocalDateTime date) {
        long percent = food.getPercentOfUsedShelfLife(date);
        if (percent > Food.SHELF_LIFE_PERCENT_75 && percent < Food.SHELF_LIFE_PERCENT_100) {
            food.setPrice(food.getPrice() * (1 - food.getDiscount() / 100));
        }
    }
}
