package ru.job4j.template;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class GeneratorTest {

    @Test
    void whenTemplateAndArgsAreCorrectThenReturnResultString() {
        Map<String, String> args = new HashMap<>();
        args.put("name", "Petr");
        args.put("subject", "you");
        assertThat(new GeneratorImpl().produce("I am ${name} you are ${subject}.", args))
                .isEqualTo("I am Petr you are you.");
    }

    @Test
    void whenTemplateIsNullThenThrowException() {
        Map<String, String> args = new HashMap<>();
        args.put("1", "one");
        assertThatThrownBy(() -> new GeneratorImpl().produce(null, args))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenArgsAreNullThenThrowException() {
        assertThatThrownBy(() -> new GeneratorImpl().produce("I am ${name}", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenTemplateContainsKeysThatAreNotExistInArgsThenThrowException() {
        Map<String, String> args = new HashMap<>();
        args.put("1", "one");
        assertThatThrownBy(() -> new GeneratorImpl().produce("${1}, ${2}", args))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenArgsContainsKeysThatAreNotExistInTemplateThenThrowException() {
        Map<String, String> args = new HashMap<>();
        args.put("1", "one");
        args.put("2", "two");
        assertThatThrownBy(() -> new GeneratorImpl().produce("${1}", args))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenArgsContainsNullValueThenReplaceWithDash() {
        Map<String, String> args = new HashMap<>();
        args.put("name", null);
        assertThat(new GeneratorImpl().produce("I am ${name}", args))
                .isEqualTo("I am -");
    }
}
