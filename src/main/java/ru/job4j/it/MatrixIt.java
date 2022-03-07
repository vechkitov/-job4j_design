package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {

    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
        setPointer();
    }

    @Override
    public boolean hasNext() {
        return row < data.length && column < data[row].length;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int rsl = data[row][column];
        movePointer();
        return rsl;
    }

    private void setPointer() {
        while (row < data.length) {
            if (column < data[row].length) {
                return;
            }
            row++;
        }
    }

    private void movePointer() {
        column++;
        while (row < data.length) {
            if (column < data[row].length) {
                return;
            }
            row++;
            column = 0;
        }
    }
}
