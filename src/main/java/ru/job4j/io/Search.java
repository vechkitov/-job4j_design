package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {

    public static void main(String[] args) throws IOException {
        validateParams(args);
        Path start = Paths.get(args[0]);
        search(start, p -> p.toFile().getName().endsWith(args[1]))
                .forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    public static void validateParams(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Программа должна принимать 2 параметра: начальный"
                    + " каталог и расширение файла. Например: java -jar search.jar c:\\temp .java");
        }
        File rootFolder = new File(args[0]);
        if (!rootFolder.exists()) {
            throw new IllegalArgumentException(
                    String.format("Неверно указан 1-й параметр: каталога '%s' не существует.",
                            rootFolder.getAbsoluteFile())
            );
        }
        if (!rootFolder.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Неверно указан 1-й параметр: '%s' не является каталогом.",
                            rootFolder.getAbsoluteFile())
            );
        }
        if (!args[1].matches("\\..+")) {
            throw new IllegalArgumentException(
                    String.format("Неверно указан 2-й параметр: расширение файла должно иметь вид '.*'. "
                            + "Например: .java", rootFolder.getAbsoluteFile())
            );
        }
    }
}
