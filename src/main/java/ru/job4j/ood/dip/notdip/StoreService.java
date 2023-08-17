package ru.job4j.ood.dip.notdip;

/**
 * Класс, моделирующий работу склада
 */
public class StoreService {

    /* Нарушение принципа DIP, т.к. зависим не от абстракции, а от реализации */
    private DocumentService documentService;
}

/**
 * Класс для формирования складских документов
 */
class DocumentService {

}
