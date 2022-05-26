package ru.job4j.io.duplicates;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private final Map<FileProperty, Set<String>> duplicates = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        File f = file.toFile();
        FileProperty fileProperty = new FileProperty(f.length(), f.getName());
        duplicates.putIfAbsent(fileProperty, new HashSet<>());
        duplicates.get(fileProperty)
                .add(f.getAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    public void printDuplicates() {
        duplicates.values().stream()
                .filter(set -> set.size() > 1)
                .flatMap(Collection::stream)
                .forEach(System.out::println);
    }
}
