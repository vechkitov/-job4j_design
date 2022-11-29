package ru.job4j.cache.menu;

import ru.job4j.cache.DirFileCache;

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
    private static final int CREATE_CACHE = 1;
    private static final int LOAD_DATA = 2;
    private static final int GET_DATA = 3;
    private static final String MENU = String.format("""
                    %nМеню:
                    %d. Создать кэш
                    %d. Загрузить содержимое файла в кэш
                    %d. Получить содержимое файла из кэша
                    Любая другая цифра - для выхода
                    Выбор: """,
            CREATE_CACHE, LOAD_DATA, GET_DATA);

    public static void main(String[] args) throws IOException {
        DirFileCache cache = null;
        try (var scanner = new Scanner(System.in)) {
            int i;
            do {
                System.out.printf(MENU);
                i = Integer.parseInt(scanner.nextLine());
                if (i == CREATE_CACHE) {
                    cache = createCache(cache, scanner);
                } else if (i == LOAD_DATA) {
                    loadData(cache, scanner);
                } else if (i == GET_DATA) {
                    getData(cache, scanner);
                } else {
                    System.out.println("Завершение работы.");
                }
            } while (i >= 1 && i <= 3);
        }
    }

    private static DirFileCache createCache(DirFileCache cache, Scanner scanner) {
        System.out.println("Укажите полный путь к кэшируемой директории:");
        String dir = scanner.nextLine();
        if (Files.isDirectory(Path.of(dir))) {
            cache = new DirFileCache(dir);
            System.out.println("Кэш создан");
        } else {
            System.err.printf("Директории не существует: %s%n", dir);
        }
        return cache;
    }

    private static void loadData(DirFileCache cache, Scanner scanner) {
        if (cache == null) {
            System.err.println("Кэш еще не создан. Создайте кэш, выбрав соответствующий пункт меню");
            return;
        }
        System.out.println("Введите ключ данных:");
        String key = scanner.nextLine();
        try {
            cache.put(key, null); /* очищаем старые данные в кэше */
            if (cache.get(key) != null) { /* загружаем новые данные */
                System.out.printf("Данные для ключа '%s' загружены%n", key);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void getData(DirFileCache cache, Scanner scanner) {
        if (cache == null) {
            System.err.println("Кэш еще не создан. Создайте кэш, выбрав соответствующий пункт меню");
            return;
        }
        System.out.println("Введите ключ данных:");
        System.out.printf("Получены данные:%n%s%n", cache.get(scanner.nextLine()));
    }
}
