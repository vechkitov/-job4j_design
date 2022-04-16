package ru.job4j.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);
        assertThat(input, is(Arrays.asList(1, 2, 3)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);
        assertThat(input, is(Arrays.asList(0, 1, 2, 3)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddAfterWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addAfter(input, 2, 2);
    }

    @Test
    public void whenRemoveIfArgumentIsEven() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeIf(input, el -> el % 2 == 0);
        assertThat(input, is(Arrays.asList(1, 3)));
    }

    @Test
    public void whenRemoveNothing() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeIf(input, el -> el > 3);
        assertThat(input, is(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void whenConditionIsTrueThanSetNewValue() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.replaceIf(input, el -> el % 2 == 0, 5);
        assertThat(input, is(Arrays.asList(1, 5, 3)));
    }

    @Test
    public void whenConditionIsFalseThanListIsTheSame() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.replaceIf(input, el -> el > 3, 5);
        assertThat(input, is(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void whenListContainsElementsThanTheyWillBeRemoved() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        ListUtils.removeAll(input, List.of(1, 3));
        assertThat(input, is(Arrays.asList(2, 4)));
    }

    @Test
    public void whenListDoNotContainsElementsThanListIsTheSame() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeAll(input, List.of(5, 6));
        assertThat(input, is(Arrays.asList(1, 2, 3)));
    }
}
