package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class MenuPrinterImplTest {
    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenPrint() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        MenuPrinterImpl printer = new MenuPrinterImpl();
        PrintStream stdOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        printer.print(menu);
        System.setOut(stdOut);
        String expected = """
                1. Сходить в магазин
                  1.1. Купить продукты
                    1.1.1. Купить хлеб
                    1.1.2. Купить молоко
                2. Покормить собаку
                """;
        assertThat(baos.toString()).isEqualTo(expected);
    }
}
