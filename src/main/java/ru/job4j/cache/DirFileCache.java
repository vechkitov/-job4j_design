package ru.job4j.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {
    private static final Logger LOG = LoggerFactory.getLogger(DirFileCache.class);
    /**
     * Абсолютный путь к кэшируемой директории
     */
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        LOG.info("Загрузка данных из источника для ключа: {}", key);
        String value = null;
        Path file = Path.of(cachingDir).resolve(key);
        try {
            value = Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error("Не могу прочитать из файла: {}", file);
        }
        return value;
    }
}
