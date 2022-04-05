package ru.job4j.list;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Размер массива должен быть больше 0");
        }
        this.container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        modCount++;
        if (size == container.length) {
            container = Arrays.copyOf(container, container.length * 2);
        }
        container[size++] = value;
    }

    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        modCount++;
        T oldValue = container[index];
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
}
