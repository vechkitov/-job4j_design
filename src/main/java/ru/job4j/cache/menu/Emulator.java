package ru.job4j.cache.menu;

import ru.job4j.cache.AbstractCache;
import ru.job4j.cache.factory.DirFileCacheFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Эмуляция работы пользователя с кэшом. Возможности:
 * - указать кэшируемую директорию
 * - загрузить содержимое файла в кэш
 * - получить содержимое файла из кэша
 */
public class Emulator {
    private static AbstractCache<String, String> cache = null;
    private static final String MENU = """
            %nМеню:
            1. Создать кэш
            2. Загрузить содержимое файла в кэш
            3. Получить содержимое файла из кэша
            Любая другая цифра - для выхода
            Выбор: """;

    public static void main(String[] args) throws IOException {
        try (var scanner = new Scanner(System.in)) {
            int i;
            do {
                System.out.printf(MENU);
                i = Integer.parseInt(scanner.nextLine());
                if (i == 1) {
                    cache = createCache(scanner);
                } else if (i == 2) {
                    loadData(scanner);
                } else if (i == 3) {
                    getData(scanner);
                } else {
                    System.out.println("Завершение работы.");
                }
            } while (i >= 1 && i <= 3);
        }
    }

    private static AbstractCache<String, String> createCache(Scanner scanner) {
        System.out.println("Укажите полный путь к кэшируемой директории:");
        String dir = scanner.nextLine();
        if (Files.isDirectory(Path.of(dir))) {
            cache = new DirFileCacheFactory(dir).createCache();
            if (cache != null) {
                System.out.println("Кэш создан");
            }
        } else {
            System.err.printf("Директории не существует: %s%n", dir);
        }
        return cache;
    }

    private static void loadData(Scanner scanner) {
        if (cache == null) {
            System.err.println("Кэш еще не создан. Создайте кэш, выбрав соответствующий пункт меню");
            return;
        }
        System.out.println("Введите ключ данных:");
        String key = scanner.nextLine();
        try {
            cache.load(key);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        if (cache.get(key) != null) {
            System.out.printf("Данные для ключа '%s' загружены%n", key);
        }
    }

    private static void getData(Scanner scanner) {
        if (cache == null) {
            System.err.println("Кэш еще не создан. Создайте кэш, выбрав соответствующий пункт меню");
            return;
        }
        System.out.println("Введите ключ данных:");
        String key = scanner.nextLine();
        String data = cache.get(key);
        if (data == null) {
            System.err.printf("Данных для ключа '%s' в кэше нет. Пытаюсь загрузить...%n", key);
            try {
                cache.load(key);
                data = cache.get(key);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        if (data != null) {
            System.out.printf("Получены данные:%n%s%n", data);
        }
    }
}
