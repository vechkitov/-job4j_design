package ru.job4j.ood.dip.notdip;

/**
 * Класс, моделирующий работу больницы
 */
public class Hospital {

    /* Нарушение принципа DIP, т.к. зависим не от абстракции, а от реализации */
    private ChildrensDepartment department;
}

/**
 * Детское отделение
 */
class ChildrensDepartment {

}
