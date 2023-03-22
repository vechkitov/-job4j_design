package ru.job4j.ood.lsp;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTest {

    @Test
    void whenShelfLifeUsedIs3DaysAndShelfLifeTotalIs10DaysThenPercentOfUsedShelfLifeIs30() {
        var createDate = LocalDateTime.of(2000, 1, 1, 12, 0);
        var expiryDate = LocalDateTime.of(2000, 1, 11, 12, 0);
        var date = LocalDateTime.of(2000, 1, 4, 12, 0);
        var food = new Food("foo", createDate, expiryDate, 0, 0);
        assertThat(food.getPercentOfUsedShelfLife(date)).isEqualTo(30);
    }
}
