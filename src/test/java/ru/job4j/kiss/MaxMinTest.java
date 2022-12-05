package ru.job4j.kiss;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class MaxMinTest {

    @Test
    void max() {
        Assertions.assertThat(
                new MaxMin().max(List.of("3", "4", "1", "2"), Comparator.naturalOrder())
        ).isEqualTo("4");
    }

    @Test
    void min() {
        Assertions.assertThat(
                new MaxMin().min(List.of("3", "4", "1", "2"), Comparator.naturalOrder())
        ).isEqualTo("1");
    }

    @Test
    void whenListIsEmptyThenMaxReturnsNull() {
        Assertions.assertThat(
                new MaxMin().max(new LinkedList<String>(), Comparator.naturalOrder())
        ).isNull();
    }

    @Test
    void whenListIsEmptyThenMinReturnsNull() {
        Assertions.assertThat(
                new MaxMin().min(new LinkedList<String>(), Comparator.naturalOrder())
        ).isNull();
    }
}
