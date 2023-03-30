package ru.job4j.ood.isp.notisp;

/**
 * Ноутбук.
 * Есть ноутбуки с CD-ROM, а есть без них. Для ноутбуков без CD-ROM метод readFromCdRom()
 * будет лишним.
 */
public interface Laptop {

    /**
     * Включить
     */
    void on();

    /**
     * Выключить
     */
    void off();

    /**
     * Выполнить программу
     */
    void executeProgram();

    /**
     * Прочитать с CD-ROM'а
     */
    void readFromCdRom();
}
