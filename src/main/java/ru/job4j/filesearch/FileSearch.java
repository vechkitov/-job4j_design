package ru.job4j.filesearch;

import ru.job4j.io.ArgsName;
import ru.job4j.io.SearchFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.function.Predicate;

import static ru.job4j.filesearch.ParamsValidator.*;

public class FileSearch {

    private static final Charset CHARSET_WIN_1251 = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        ParamsValidator.validateParams(argsName);
        SearchFiles searchFiles = new SearchFiles(getCondition(argsName));
        Files.walkFileTree(
                Path.of(argsName.get(DIR_PARAM)), searchFiles);
        saveSearchResult(argsName.get(OUT_PARAM), searchFiles.getPaths());
    }

    private static Predicate<Path> getCondition(ArgsName argsName) {
        Predicate<Path> condition;
        String searchType = argsName.get(TYPE_PARAM);
        String searchStr = argsName.get(NAME_PARAM);
        if (searchType.equals(MASK_SEARCH_TYPE)) {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher(String.format("glob:**/%s", searchStr));
            condition = matcher::matches;
        } else if (searchType.equals(NAME_SEARCH_TYPE)) {
            condition = path -> path.toFile().getName().equals(searchStr);
        } else if (searchType.equals(REGEX_SEARCH_TYPE)) {
            condition = path -> path.toFile().getName().matches(searchStr);
        } else {
            throw new IllegalArgumentException(
                    String.format("Неизвестный тип поиска: %s%n%s", searchStr, allowableSearchTypesMessage())
            );
        }
        return condition;
    }

    private static void saveSearchResult(String resFileName, List<Path> foundFiles) throws IOException {
        try (final var out = new PrintWriter(resFileName, CHARSET_WIN_1251)) {
            foundFiles.forEach(out::println);
        }
    }
}
