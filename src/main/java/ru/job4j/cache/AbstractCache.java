package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) {
        SoftReference<V> refValue = cache.get(key);
        return refValue != null ? refValue.get() : null;
    }

    /**
     * Загружает данные в кэш.
     *
     * @param key ключ, по которому будут доступны загружаемые данные
     * @return загруженные данные
     */
    public abstract V load(K key);
}
