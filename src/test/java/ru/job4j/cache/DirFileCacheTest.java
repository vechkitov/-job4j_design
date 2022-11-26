package ru.job4j.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DirFileCacheTest {

    @Test
    void whenLoadThenDataExistsInTheCache(@TempDir Path tempDir) throws IOException {
        final String fileName = "temp.txt";
        String expected = String.format("один %s два", System.lineSeparator());
        Path tempFile = Files.writeString(tempDir.resolve(fileName), expected);
        DirFileCache cache = new DirFileCache(tempDir.toString());
        /* После выполнения метода cache.load() на загруженные из файла данные будет только soft-ссылка
         * из кэша. Поэтому, при нехватке памяти, GC может удалить эти загруженные данные из кэша до выполнения
         * метода assertThat. Чтобы этого не произошло, создадим на эти данные strong-ссылку content. */
        String content = cache.load(tempFile.toFile().getName());
        /* System.gc(); */
        assertThat(cache.get(fileName)).isEqualTo(expected);
    }
}
