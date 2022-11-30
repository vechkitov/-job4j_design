package ru.job4j.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractCache.class);
    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        LOG.info("Загрузка данных в кэш для ключа: {}", key);
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) {
        LOG.info("Получение данных по ключу: {}", key);
        V value = cache.getOrDefault(key, new SoftReference<>(null)).get();
        if (value == null) {
            value = load(key);
            put(key, value);
        }
        return value;
    }

    /**
     * Загружает данные в кэш.
     *
     * @param key ключ, по которому будут доступны загружаемые данные
     * @return загруженные данные
     */
    protected abstract V load(K key);
}
