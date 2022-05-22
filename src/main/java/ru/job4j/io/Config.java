package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private static final String SKIPPED_PATTERN = "^(\\s*#.*|\\s*$)";
    private static final String VALID_PATTERN = "\\S+=\\s*\\S+.*";

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.matches(SKIPPED_PATTERN)) {
                    continue;
                }
                if (!line.matches(VALID_PATTERN)) {
                    throw new IllegalArgumentException("Не соответствие формату \"ключ=значение\": " + line);
                }
                String[] pair = line.split("=", 2);
                values.put(pair[0], pair[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        values.forEach((k, v) -> out.add(k + "=" + v));
        return out.toString();
    }

    public static void main(String[] args) {
        Config config = new Config("app.properties");
        config.load();
        System.out.println(config);
    }
}
