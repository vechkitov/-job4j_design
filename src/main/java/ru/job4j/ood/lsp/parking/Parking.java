package ru.job4j.ood.lsp.parking;

/**
 * Сервис парковки автомобилей
 */
public interface Parking {

    /**
     * Занимает парковочное место автомобилем.
     *
     * @param vehicle Автомобиль
     */
    void takeSpace(Vehicle vehicle);

    /**
     * Освобождает парковочное место.
     *
     * @param vehicle Автомобиль
     */
    void freeSpace(Vehicle vehicle);
}
