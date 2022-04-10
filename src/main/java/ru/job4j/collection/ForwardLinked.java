package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForwardLinked<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int modCount;
    private int size;

    public void add(T value) {
        modCount++;
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public void addFirst(T value) {
        modCount++;
        Node<T> newNode = new Node(value);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("Элемента не существует.");
        }
        Node<T> removed = head;
        T value = removed.value;
        head = removed.next;
        removed.value = null;
        removed.next = null;
        size--;
        return value;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            private Node<T> node = head;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("Во время обхода коллекции она была изменена");
                }
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Элемента не существует.");
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private class Node<T> {
        private T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
