package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {

    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        List<String> strings = new SimpleConvert().toList("first", "second", "three", "four", "five");
        assertThat(strings).isNotEmpty()
                .hasSize(5)
                .contains("four", "second")
                .containsAnyOf("five", "fifteen", "fifty")
                .startsWith("first")
                .allSatisfy(s -> assertThat(s.length()).isGreaterThan(3))
                .allMatch(s -> s.length() > 3)
                .filteredOn(s -> s.startsWith("f"))
                .hasSize(3);
    }

    @Test
    void checkOneElementOfList() {
        List<String> strings = new SimpleConvert().toList("first", "second", "three", "four", "five");
        assertThat(strings).element(0)
                .isNotNull()
                .asString()
                .contains("f");
    }

    @Test
    void checkSet() {
        Set<String> strings = new SimpleConvert().toSet("first", "second", "three", "four", "five");
        assertThat(strings).isNotNull()
                .anyMatch("four"::equals)
                .containsOnlyOnce("five")
                .doesNotContainNull()
                .anySatisfy(s -> assertThat(s).contains("ee"))
                .noneMatch("six"::equals);
    }

    @Test
    void checkOneElementOfSet() {
        Set<String> strings = new SimpleConvert().toSet("first", "second", "three", "four", "five");
        assertThat(strings).first()
                .matches(s -> !s.isBlank())
                .isNotIn("zero", "six");
    }

    @Test
    void checkMap() {
        Map<String, Integer> map = new SimpleConvert().toMap("first", "second", "three", "four", "five");
        assertThat(map).isNotEmpty()
                .hasSize(5)
                .containsKeys("first", "five")
                .containsValues(0, 4)
                .doesNotContainKey("")
                .doesNotContainValue(5)
                .containsEntry("first", 0);
    }
}
