package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        modCount++;
        if ((float) count / capacity >= LOAD_FACTOR) {
            expand();
        }
        final int i = indexFor(key);
        final boolean rsl = table[i] == null;
        if (rsl) {
            table[i] = new MapEntry<>(key, value);
            count++;
        }
        return rsl;
    }

    @Override
    public V get(K key) {
        final int i = indexFor(key);
        return table[i] == null ? null : table[i].value;
    }

    @Override
    public boolean remove(K key) {
        modCount++;
        final int i = indexFor(key);
        final boolean rsl = table[i] != null;
        if (rsl) {
            table[i] = null;
            count--;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private final int expected = modCount;
            private int index = 0;

            @Override
            public boolean hasNext() {
                if (expected != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < capacity && table[index] == null) {
                    index++;
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    public int size() {
        return count;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(K key) {
        return (table.length - 1) & hash(key == null ? 0 : key.hashCode());
    }

    private void expand() {
        MapEntry<K, V>[] oldTable = table;
        capacity <<= 1;
        table = new MapEntry[capacity];
        for (MapEntry<K, V> entry : oldTable) {
            final int i = indexFor(entry.key);
            table[i] = new MapEntry<>(entry.key, entry.value);
        }
    }

    private static class MapEntry<K, V> {
        private K key;
        private V value;

        private MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
