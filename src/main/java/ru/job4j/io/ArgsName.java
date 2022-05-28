package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        String value = values.get(key);
        if (value == null) {
            throw new IllegalArgumentException(
                    String.format("Неверное имя параметра: '%s'. Доступные имена параметров: %s", key, values.keySet()));
        }
        return value;
    }

    private void parse(String[] args) {
        for (String arg : args) {
            if (!arg.matches("-\\S+=\\s*\\S+.*")) {
                throw new IllegalArgumentException(String.format("Не соответствие формату 'ключ=значение': %s", arg));
            }
            String[] pair = arg.split("=", 2);
            values.put(pair[0].substring(1), pair[1]);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[]{"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[]{"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}
