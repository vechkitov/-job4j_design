package ru.job4j.io;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArgsNameTest {

    @Test
    public void whenGetFirst() {
        ArgsName jvm = ArgsName.of(new String[]{"-Xmx=512", "-encoding=UTF-8"});
        assertThat(jvm.get("Xmx"), is("512"));
    }

    @Test
    public void whenGetFirstReorder() {
        ArgsName jvm = ArgsName.of(new String[]{"-encoding=UTF-8", "-Xmx=512"});
        assertThat(jvm.get("Xmx"), is("512"));
    }

    @Test
    public void whenMultipleEqualsSymbol() {
        ArgsName jvm = ArgsName.of(new String[]{"-request=?msg=Exit="});
        assertThat(jvm.get("request"), is("?msg=Exit="));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetNotExist() {
        ArgsName.of(new String[]{"-Xmx=512"})
                .get("Xms");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongSomeArgument() {
        ArgsName.of(new String[]{"-enconding=UTF-8", "-Xmx="});
    }

    @Test
    public void whenArgsNotExist() {
        ArgsName jvm = ArgsName.of(new String[0]);
        assertThat(jvm.allArgsNames().size(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenKeyNotExist() {
        ArgsName.of(new String[]{"-=512"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEqualSingNotExist() {
        ArgsName.of(new String[]{"-Xmx:512"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusSingNotExist() {
        ArgsName.of(new String[]{"Xmx=512"});
    }
}
