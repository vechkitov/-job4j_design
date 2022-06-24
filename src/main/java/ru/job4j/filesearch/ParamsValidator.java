package ru.job4j.filesearch;

import ru.job4j.io.ArgsName;

import java.io.File;
import java.util.Set;

/**
 * Проверяет корректность введенных параметров строки запуска приложения.
 */
public class ParamsValidator {

    public static final String DIR_PARAM = "d";
    public static final String NAME_PARAM = "n";
    public static final String TYPE_PARAM = "t";
    public static final String OUT_PARAM = "o";
    public static final Set<String> PARAM_NAMES = Set.of(DIR_PARAM, NAME_PARAM, TYPE_PARAM, OUT_PARAM);
    public static final String MASK_SEARCH_TYPE = "mask";
    public static final String NAME_SEARCH_TYPE = "name";
    public static final String REGEX_SEARCH_TYPE = "regex";
    public static final Set<String> SEARCH_TYPES = Set.of(MASK_SEARCH_TYPE, NAME_SEARCH_TYPE, REGEX_SEARCH_TYPE);

    public static void validateParams(ArgsName argsName) {
        Set<String> allArgsNames = argsName.allArgsNames();
        if (allArgsNames.size() != 4) {
            throw new IllegalArgumentException(
                    String.format("Неверное количество параметров запуска: %d %s%n%s",
                            allArgsNames.size(), allArgsNames, requiredParamsMessage()));
        }
        for (String argName : argsName.allArgsNames()) {
            if (!PARAM_NAMES.contains(argName)) {
                throw new IllegalArgumentException(
                        String.format("Неверное имя параметра (%s)%n%s", argName, requiredParamsMessage()));
            }
        }
        File rootFolder = new File(argsName.get(DIR_PARAM));
        if (!rootFolder.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("'%s' не является каталогом.", rootFolder.getAbsoluteFile())
            );
        }
        if (!rootFolder.exists()) {
            throw new IllegalArgumentException(
                    String.format("Каталога '%s' не существует.", rootFolder.getAbsoluteFile())
            );
        }
        String searchType = argsName.get(TYPE_PARAM);
        if (!SEARCH_TYPES.contains(searchType)) {
            throw new IllegalArgumentException(
                    String.format("Неверное значение параметра -%s (%s). %s",
                            TYPE_PARAM, searchType, allowableSearchTypesMessage())
            );
        }
    }

    public static String allowableSearchTypesMessage() {
        return String.format("Допустимые значения типов поиска:%n"
                + "mask - по маске%n"
                + "name - по полному совпадение%n"
                + "regex - по регулярному выражению");
    }

    private static String requiredParamsMessage() {
        return String.format("Программа должна принимать 4 параметра в произвольном порядке:%n"
                + "-d=[directory] - путь к каталогу, в котором надо найти файл%n"
                + "-n=[name] - одно из: имя файла, маска, регулярное выражение%n"
                + "-t=[type] - тип поиска: 'mask' - искать по маске; 'name' - по полному совпадение "
                + "имени; 'regex' - по регулярному выражению%n"
                + "-o=[output] - имя файла с результатами поиска");
    }
}
