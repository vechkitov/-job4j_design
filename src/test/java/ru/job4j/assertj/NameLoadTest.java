package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameLoadTest {

    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void whenLengthOfArgsIsEquals0ThenThrowException() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is empty");
    }

    @Test
    void whenArgDoesNotContainTheEqualSymbolThenThrowException() {
        NameLoad nameLoad = new NameLoad();
        String arg = "one:one";
        assertThatThrownBy(() -> nameLoad.parse(arg))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(arg)
                .hasMessageContaining("=");
    }

    @Test
    void whenArgDoesNotContainKeyThenThrowException() {
        NameLoad nameLoad = new NameLoad();
        String arg = "=one";
        assertThatThrownBy(() -> nameLoad.parse(arg))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(arg)
                .hasMessageContaining("key");
    }

    @Test
    void whenArgDoesNotContainValueThenThrowException() {
        NameLoad nameLoad = new NameLoad();
        String arg = "one=";
        assertThatThrownBy(() -> nameLoad.parse(arg))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(arg)
                .hasMessageContaining("value");
    }
}
