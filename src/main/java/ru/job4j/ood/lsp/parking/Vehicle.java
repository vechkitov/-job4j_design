package ru.job4j.ood.lsp.parking;

import java.util.Objects;

/**
 * Легковой или грузовой автомобиль
 */
public class Vehicle {

    /**
     * Определяет тип автомобиля. Легковой, если size равен 1, грузовой, если размер > 1
     */
    private final int size;

    public Vehicle(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Размер автомобиля не может быть меньше 1, но был: " + size);
        }
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return size == vehicle.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }
}
