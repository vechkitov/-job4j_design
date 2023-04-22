package ru.job4j.ood.isp.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class TodoApp {
    private static final ActionDelegate STUB_ACTION = () -> System.out.println("Some action");
    private static Menu menu = new SimpleMenu();
    private static MenuPrinter printer = new MenuPrinterImpl();

    public static void main(String[] args) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                printOptions();
                int n = Integer.parseInt(reader.readLine());
                if (n == 1) {
                    addRootItem(reader);
                } else if (n == 2) {
                    addChildItem(reader);
                } else if (n == 3) {
                    doItemAction(reader);
                } else if (n == 4) {
                    printMenu();
                } else {
                    System.out.println("Работа завершена.");
                    break;
                }
            }
        }
    }

    private static void printOptions() {
        System.out.println("""
                Выберите действие:
                1 - Добавить элемент в корень меню
                2 - Добавить элемент к родительскому элементу
                3 - Вызвать действие элемента меню
                4 - Распечатать всё меню
                Любое другое число - завершение работы
                """);
    }

    /**
     * Добавляет элемент в корень меню
     */
    private static void addRootItem(BufferedReader reader) throws IOException {
        System.out.println("Введите имя корневого элемента меню:");
        String itemName = reader.readLine();
        menu.add(Menu.ROOT, itemName, STUB_ACTION);
        System.out.println("Элемент добавлен");
    }

    /**
     * Добавляет элемент к родительскому элементу
     */
    private static void addChildItem(BufferedReader reader) throws IOException {
        System.out.println("Введите имя элемента меню:");
        String itemName = reader.readLine();
        System.out.println("Введите имя родительского элемента:");
        String parentName = reader.readLine();
        menu.add(parentName, itemName, STUB_ACTION);
        System.out.println("Элемент добавлен");
    }

    /**
     * Вызывает действие, привязанное к пункту меню
     */
    private static void doItemAction(BufferedReader reader) throws IOException {
        System.out.println("Введите имя элемента меню:");
        String itemName = reader.readLine();
        Optional<Menu.MenuItemInfo> select = menu.select(itemName);
        if (select.isPresent()) {
            select.get().getActionDelegate().delegate();
        } else {
            System.out.printf("Не удалось выбрать элемент меню с именем '%s'\n", itemName);
        }
    }

    /**
     * Выводит меню в консоль
     */
    private static void printMenu() {
        printer.print(menu);
    }
}
