package ru.job4j.cache.factory;

import ru.job4j.cache.AbstractCache;
import ru.job4j.cache.DirFileCache;

public class DirFileCacheFactory extends CacheFactory {
    private final String cachingDir;

    public DirFileCacheFactory(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    public AbstractCache<String, String> createCache() {
        return new DirFileCache(cachingDir);
    }
}
