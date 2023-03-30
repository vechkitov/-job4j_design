package ru.job4j.ood.isp.notisp;

/**
 * Птица.
 * Не все птицы умеют летать, поэтому для Пингвина метод fly() будет лишним
 */
public interface Bird {

    /**
     * Идти
     */
    void go();

    /**
     * Есть
     */
    void eat();

    /**
     * Лететь
     */
    void fly();
}
