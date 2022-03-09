package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
        setPointer();
    }

    @Override
    public boolean hasNext() {
        return index < data.length;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int el = data[index];
        movePointer();
        return el;
    }

    private void setPointer() {
        while (index < data.length) {
            if (data[index] % 2 == 0) {
                return;
            }
            index++;
        }
    }

    private void movePointer() {
        index++;
        setPointer();
    }
}
