package ru.job4j.ood.srp.notsrp;

/**
 * Подразумевается, что в классе будет содержаться некая логика.
 * Нарушение SRP заключается в том, что помимо основного функционала класс
 * еще и контролирует количества своих экземпляров.
 */
public final class Singleton {

    private static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
