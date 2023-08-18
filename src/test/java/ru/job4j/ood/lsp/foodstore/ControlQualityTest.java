package ru.job4j.ood.lsp.foodstore;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ControlQualityTest {
    Shop shop = new Shop();
    Trash trash = new Trash();
    Warehouse warehouse = new Warehouse();
    ControlQuality cq = new ControlQuality(Set.of(shop, trash, warehouse));

    @Test
    void whenPercentOfUsedShelfLifeIsLessThan25() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 11, 12, 0);
        var date = LocalDateTime.of(2000, 1, 2, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        cq.allocateFoodToStore(food, date);
        assertThat(food.getPercentOfUsedShelfLife(date)).isLessThan(Food.SHELF_LIFE_PERCENT_25);
        assertThat(warehouse.getFoods().size()).isEqualTo(1);
        assertThat(shop.getFoods().size()).isEqualTo(0);
        assertThat(trash.getFoods().size()).isEqualTo(0);
    }

    @Test
    void whenPercentOfUsedShelfLifeIsEqualsTo25() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 6, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        cq.allocateFoodToStore(food, date);
        assertThat(food.getPercentOfUsedShelfLife(date)).isEqualTo(Food.SHELF_LIFE_PERCENT_25);
        assertThat(shop.getFoods().size()).isEqualTo(1);
        assertThat(warehouse.getFoods().size()).isEqualTo(0);
        assertThat(trash.getFoods().size()).isEqualTo(0);
    }

    @Test
    void whenPercentOfUsedShelfLifeIsGreaterThan25AndLessThan100() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 11, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        cq.allocateFoodToStore(food, date);
        assertThat(food.getPercentOfUsedShelfLife(date)).isGreaterThan(Food.SHELF_LIFE_PERCENT_25);
        assertThat(food.getPercentOfUsedShelfLife(date)).isLessThan(Food.SHELF_LIFE_PERCENT_100);
        assertThat(shop.getFoods().size()).isEqualTo(1);
        assertThat(warehouse.getFoods().size()).isEqualTo(0);
        assertThat(trash.getFoods().size()).isEqualTo(0);
    }

    @Test
    void whenPercentOfUsedShelfLifeIsGreaterThan75AndLessThan100ThenDiscountIsApplied() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 11, 12, 0);
        var date = LocalDateTime.of(2000, 1, 10, 12, 0);
        double priceInit = 100d;
        double discount = 30d;
        double priceExpected = 70d;
        var food = new Food("foo", createDate, expiryDate, discount, priceInit);
        cq.allocateFoodToStore(food, date);
        assertThat(food.getPercentOfUsedShelfLife(date)).isGreaterThan(Food.SHELF_LIFE_PERCENT_75);
        assertThat(food.getPercentOfUsedShelfLife(date)).isLessThan(Food.SHELF_LIFE_PERCENT_100);
        assertThat(food.getPrice()).isEqualTo(priceExpected);
    }

    @Test
    void whenPercentOfUsedShelfLifeIsEqualsTo100() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 21, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        cq.allocateFoodToStore(food, date);
        assertThat(food.getPercentOfUsedShelfLife(date)).isEqualTo(Food.SHELF_LIFE_PERCENT_100);
        assertThat(trash.getFoods().size()).isEqualTo(1);
        assertThat(shop.getFoods().size()).isEqualTo(0);
        assertThat(warehouse.getFoods().size()).isEqualTo(0);
    }

    @Test
    void whenPercentOfUsedShelfLifeIsGreaterThan100() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 22, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        cq.allocateFoodToStore(food, date);
        assertThat(food.getPercentOfUsedShelfLife(date)).isGreaterThan(Food.SHELF_LIFE_PERCENT_100);
        assertThat(trash.getFoods().size()).isEqualTo(1);
        assertThat(shop.getFoods().size()).isEqualTo(0);
        assertThat(warehouse.getFoods().size()).isEqualTo(0);
    }

    @Test
    void whenResortThanCorrectAllocation() {
        var controlDate = LocalDateTime.of(2000, 1, 6, 12, 0);
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate1 = LocalDateTime.of(2000, 1, 30, 12, 0);
        var warehouseFood = new Food("warehouseFood", createDate, expiryDate1, 0, 0);
        var expiryDate2 = LocalDateTime.of(2000, 1, 11, 12, 0);
        var shopFood = new Food("shopFood", createDate, expiryDate2, 0, 0);
        var expiryDate3 = LocalDateTime.of(2000, 1, 5, 12, 0);
        var trashFood = new Food("trashFood", createDate, expiryDate3, 0, 0);
        warehouse.addFood(trashFood);
        shop.addFood(shopFood);
        trash.addFood(warehouseFood);
        cq.resort(controlDate);
        assertAll(
                () -> assertThat(warehouse.getFoods()).hasSize(1),
                () -> assertThat(warehouse.getFoods()).contains(warehouseFood),
                () -> assertThat(shop.getFoods()).hasSize(1),
                () -> assertThat(shop.getFoods()).contains(shopFood),
                () -> assertThat(trash.getFoods()).hasSize(1),
                () -> assertThat(trash.getFoods()).contains(trashFood)
        );
    }
}
