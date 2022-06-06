package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CSVReader {

    private static final String PATH_PARAM = "path";
    private static final String DELIMITER_PARAM = "delimiter";
    private static final String OUT_PARAM = "out";
    private static final String FILTER_PARAM = "filter";
    private static final Set<String> PARAM_NAMES = Set.of(PATH_PARAM, DELIMITER_PARAM, OUT_PARAM, FILTER_PARAM);
    private static final String STD_OUT = "stdout";
    private static final Charset CHARSET_WIN_1251 = Charset.forName("windows-1251");

    public static void main(String[] args) throws Exception {
        handle(ArgsName.of(args));
    }

    public static void handle(ArgsName argsName) throws Exception {
        validateParams(argsName);
        List<String> lines = readFile(
                Path.of(argsName.get(PATH_PARAM)),
                argsName.get(DELIMITER_PARAM),
                Arrays.stream(argsName.get(FILTER_PARAM).split(","))
                        .collect(Collectors.toUnmodifiableSet())
        );
        printResult(lines, argsName.get(OUT_PARAM));
    }

    private static List<String> readFile(Path pathToFile, String delimiter, Set<String> filter) throws IOException {
        List<String> result = new ArrayList<>();
        List<Integer> reqColumnNums = new ArrayList<>();
        try (var scanner = new Scanner(pathToFile, CHARSET_WIN_1251)) {
            if (scanner.hasNextLine()) {
                StringJoiner stringJoiner = new StringJoiner(delimiter);
                String[] lineValues = scanner.nextLine().split(delimiter);
                for (int i = 0; i < lineValues.length; i++) {
                    if (filter.contains(lineValues[i])) {
                        reqColumnNums.add(i);
                        stringJoiner.add(lineValues[i]);
                    }
                }
                result.add(stringJoiner.toString());
            }
            while (scanner.hasNextLine()) {
                StringJoiner stringJoiner = new StringJoiner(delimiter);
                String[] lineValues = scanner.nextLine().split(delimiter);
                for (int columnNum : reqColumnNums) {
                    stringJoiner.add(lineValues[columnNum]);
                }
                result.add(stringJoiner.toString());
            }
        }
        return result;
    }

    private static void printResult(List<String> lines, String out) throws IOException {
        if (STD_OUT.equals(out)) {
            lines.forEach(System.out::println);
        } else {
            try (PrintWriter writer = new PrintWriter(out, CHARSET_WIN_1251)) {
                lines.forEach(writer::println);
            }
        }
    }

    private static void validateParams(ArgsName argsName) {
        if (argsName.allArgsNames().size() != 4) {
            throw new IllegalArgumentException(String.format(
                    "Программа должна принимать 4 параметра: %s", PARAM_NAMES));
        }
        for (String argName : argsName.allArgsNames()) {
            if (!PARAM_NAMES.contains(argName)) {
                throw new IllegalArgumentException(String.format(
                        "Неверное имя параметра: '%s'. Допустимые имена параметров: %s", argName, PARAM_NAMES));
            }
        }
        File source = new File(argsName.get(PATH_PARAM));
        if (!source.exists()) {
            throw new IllegalArgumentException(String.format("Файла не существует: '%s'", source));
        }
        if (!source.isFile()) {
            throw new IllegalArgumentException(String.format("'%s' не является файлом", source));
        }
    }
}
