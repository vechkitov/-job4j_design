package ru.job4j.ood.dip.notdip;

/**
 * Кинотеатр
 */
public class Cinema {

    /* Нарушение принципа DIP, т.к. зависим не от абстракции, а от реализации */
    private SonyFilmProjector filmProjector;
}

/**
 * Кинопроектор Sony
 */
class SonyFilmProjector {

}
