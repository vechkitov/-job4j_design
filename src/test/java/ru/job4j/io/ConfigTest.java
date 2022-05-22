package ru.job4j.io;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void whenPairWithoutComment() {
        Config config = new Config("./data/pair_without_comment.properties");
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
    }

    @Test
    public void whenPairWithCommentAndEmptyLines() {
        Config config = new Config("./data/pair_with_comment_and_empty_lines.properties");
        config.load();
        assertThat(config.toString().lines().count(), is(2L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPairWithoutKey() {
        new Config("./data/pair_without_key.properties").load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPairWithoutValue() {
        new Config("./data/pair_without_value.properties").load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPairDoesNotHaveEqualSing() {
        new Config("./data/pair_without_equal_sing.properties").load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPairHasOnlyEqualSing() {
        new Config("./data/pair_with_only_equal_sing.properties").load();
    }
}
