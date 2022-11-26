package ru.job4j.cache.factory;

import ru.job4j.cache.AbstractCache;

public abstract class CacheFactory {

    public abstract AbstractCache createCache();
}
