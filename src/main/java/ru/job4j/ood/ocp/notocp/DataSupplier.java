package ru.job4j.ood.ocp.notocp;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

/**
 * Нарушение OCP: если понадобится получать данные из другого вида коллекции, придется
 * изменять уже существующий код: изменять метод 'getData'
 */
public class DataSupplier {

    public String getData(Collection<String> data) {
        String res = null;
        if (data instanceof List<String>) {
            res = ((List<String>) data).get(0);
        } else if (data instanceof Queue<String>) {
            res = ((Queue<String>) data).peek();
        }
        return res;
    }
}
