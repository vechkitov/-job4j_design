package ru.job4j.cache;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    /**
     * Абсолютный путь к кэшируемой директории
     */
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    public String load(String key) {
        Path file = Path.of(cachingDir).resolve(key);
        try {
            String content = Files.readString(file, StandardCharsets.UTF_8);
            put(key, content);
            return content;
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Не могу прочитать из файла %s", file));
        }
    }
}
