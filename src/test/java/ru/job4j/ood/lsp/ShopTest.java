package ru.job4j.ood.lsp;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ShopTest {

    @Test
    void whenPercentOfUsedShelfLifeIsLessThan25() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 2, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        assertThat(food.getPercentOfUsedShelfLife(date)).isLessThan(Food.SHELF_LIFE_PERCENT_25);
        assertThat(new Shop().isShelfLifeCorrect(food, date)).isFalse();
    }

    @Test
    void whenPercentOfUsedShelfLifeIsEqualsTo25() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 6, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        assertThat(food.getPercentOfUsedShelfLife(date)).isEqualTo(Food.SHELF_LIFE_PERCENT_25);
        assertThat(new Shop().isShelfLifeCorrect(food, date)).isTrue();
    }

    @Test
    void whenPercentOfUsedShelfLifeIsGreaterThan25AndLessThan100() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 11, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        assertThat(food.getPercentOfUsedShelfLife(date)).isGreaterThan(Food.SHELF_LIFE_PERCENT_25);
        assertThat(food.getPercentOfUsedShelfLife(date)).isLessThan(Food.SHELF_LIFE_PERCENT_100);
        assertThat(new Shop().isShelfLifeCorrect(food, date)).isTrue();
    }

    @Test
    void whenPercentOfUsedShelfLifeIsEqualsTo100() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 21, 12, 0);
        var date = LocalDateTime.of(2000, 1, 21, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        assertThat(food.getPercentOfUsedShelfLife(date)).isEqualTo(Food.SHELF_LIFE_PERCENT_100);
        assertThat(new Shop().isShelfLifeCorrect(food, date)).isFalse();
    }
}
