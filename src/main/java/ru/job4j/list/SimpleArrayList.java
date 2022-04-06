package ru.job4j.list;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        modCount++;
        if (size == container.length) {
            grow();
        }
        container[size++] = value;
    }

    @Override
    public T set(int index, T newValue) {
        T oldValue = get(index);
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        modCount++;
        T oldValue = get(index);
        if (index < size - 1) {
            System.arraycopy(container, index + 1, container, index, size - index - 1);
        }
        container[--size] = null;
        return oldValue;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int expectedModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException("Во время обхода коллекции она была изменена");
                }
                if (!hasNext()) {
                    throw new NoSuchElementException("Элемента не существует");
                }
                return container[index++];
            }
        };
    }

    private void grow() {
        container = container.length == 0
                ? (T[]) new Object[10]
                : Arrays.copyOf(container, container.length * 2);
    }
}
