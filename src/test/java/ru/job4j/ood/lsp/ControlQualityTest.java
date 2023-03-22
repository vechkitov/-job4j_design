package ru.job4j.ood.lsp;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

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

//    @Test
//    void whenPercentOfUsedShelfLifeIsEqualsTo75() {
//        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
//        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
//        var date = LocalDateTime.of(2000, 1, 16, 12, 0);
//        var food = new Food("foo", createDate, expiryDate, 0, 0);
//        cq.allocateFoodToStore(food, date);
//        assertThat(food.getPercentOfUsedShelfLife(date)).isEqualTo(Food.SHELF_LIFE_PERCENT_75);
//        assertThat(shop.getFoods().size()).isEqualTo(1);
//        assertThat(warehouse.getFoods().size()).isEqualTo(0);
//        assertThat(trash.getFoods().size()).isEqualTo(0);
//    }

//    @Test
//    void whenPercentOfUsedShelfLifeIsGreaterThan75AndLessThan100ThenFoodIsAllocatedToShop() {
//        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
//        var expiryDate = LocalDateTime.of(2000, 1, 11, 12, 0);
//        var date = LocalDateTime.of(2000, 1, 10, 12, 0);
//        var food = new Food("foo", createDate, expiryDate, 0, 0);
//        cq.allocateFoodToStore(food, date);
//        assertThat(food.getPercentOfUsedShelfLife(date)).isGreaterThan(Food.SHELF_LIFE_PERCENT_75);
//        assertThat(food.getPercentOfUsedShelfLife(date)).isLessThan(Food.SHELF_LIFE_PERCENT_100);
//        assertThat(shop.getFoods().size()).isEqualTo(1);
//        assertThat(warehouse.getFoods().size()).isEqualTo(0);
//        assertThat(trash.getFoods().size()).isEqualTo(0);
//    }

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
}
