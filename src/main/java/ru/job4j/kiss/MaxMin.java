package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        return getExtremum(value, comparator);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return getExtremum(value, comparator.reversed());
    }

    private <T> T getExtremum(List<T> value, Comparator<T> c) {
        if (value.isEmpty()) {
            return null;
        }
        T res = value.get(0);
        for (int i = 1; i < value.size(); i++) {
            if (c.compare(value.get(i), res) > 0) {
                res = value.get(i);
            }
        }
        return res;
    }
}
