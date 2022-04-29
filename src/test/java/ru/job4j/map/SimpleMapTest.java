package ru.job4j.map;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenAdd2DifferentKeysThanMapHas2Keys() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.put("one", 1);
        map.put("two", 2);
        assertThat(map.size(), is(2));
        assertThat(map.get("one"), is(1));
        assertThat(map.get("two"), is(2));
    }

    @Test
    public void whenAdd2IdenticalKeysThanMapHasOnlyFirstAddedKey() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        assertTrue(map.put("one", 1));
        assertFalse(map.put("one", 2));
        assertThat(map.size(), is(1));
        assertThat(map.get("one"), is(1));
    }

    @Test
    public void whenAddNullKeyThanKeyWillBeAdded() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.put(null, 1);
        assertThat(map.size(), is(1));
        assertThat(map.get(null), is(1));
    }

    @Test
    public void whenDoubleRemoveTheSameKeyThanKeyWillBeRemovedOneTimeOnly() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.put("one", 1);
        map.put("two", 2);
        assertTrue(map.remove("one"));
        assertFalse(map.remove("one"));
        assertThat(map.size(), is(1));
    }

    @Test
    public void whenGetIteratorFromEmptyMapThenHasNextReturnFalse() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        assertFalse(map.iterator().hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetIteratorFromEmptyMapThenNextThrowException() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.iterator().next();
    }

    @Test
    public void whenGetIteratorTwiceThenStartAlwaysFromBeginning() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.put("one", 1);
        assertEquals("one", map.iterator().next());
        assertEquals("one", map.iterator().next());
    }

    @Test
    public void whenCheckIterator() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.put("one", 1);
        map.put("two", 2);
        Iterator<String> iterator = map.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() != null);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() != null);
        assertFalse(iterator.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddAfterGetIteratorThenMustBeException() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        Iterator<String> iterator = map.iterator();
        map.put("one", 1);
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenRemoveAfterGetIteratorThenMustBeException() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.put("one", 1);
        Iterator<String> iterator = map.iterator();
        map.remove("one");
        iterator.next();
    }
}
