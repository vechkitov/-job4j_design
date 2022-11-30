package ru.job4j.cache.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(Emulator.class);
    private static final int CREATE_CACHE = 1;
    private static final int LOAD_DATA = 2;
    private static final int GET_DATA = 3;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String MENU = String.format("""
                    %sМеню:
                    %d. Создать кэш
                    %d. Загрузить содержимое файла в кэш
                    %d. Получить содержимое файла из кэша
                    Любая другая цифра - для выхода
                    Выбор: """, LINE_SEPARATOR,
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
                    LOG.info("Завершение работы.");
                }
            } while (i >= CREATE_CACHE && i <= GET_DATA);
        }
    }

    private static DirFileCache createCache(DirFileCache cache, Scanner scanner) {
        System.out.println("Укажите полный путь к кэшируемой директории:");
        String dir = scanner.nextLine();
        if (Files.isDirectory(Path.of(dir))) {
            cache = new DirFileCache(dir);
            LOG.info("Кэш создан");
        } else {
            LOG.error("Директории не существует: {}", dir);
        }
        return cache;
    }

    private static void loadData(DirFileCache cache, Scanner scanner) {
        if (cache == null) {
            LOG.error("Кэш еще не создан. Создайте кэш, выбрав соответствующий пункт меню");
            return;
        }
        System.out.println("Введите ключ данных:");
        String key = scanner.nextLine();
        cache.put(key, null); /* очищаем старые данные в кэше */
        if (cache.get(key) != null) { /* загружаем новые данные */
            LOG.info("Данные для ключа '{}' загружены", key);
        }
    }

    private static void getData(DirFileCache cache, Scanner scanner) {
        if (cache == null) {
            LOG.error("Кэш еще не создан. Создайте кэш, выбрав соответствующий пункт меню");
            return;
        }
        System.out.println("Введите ключ данных:");
        LOG.info("Получены данные:{}{}", LINE_SEPARATOR, cache.get(scanner.nextLine()));
    }
}
