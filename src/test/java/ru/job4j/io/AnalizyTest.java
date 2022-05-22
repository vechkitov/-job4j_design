package ru.job4j.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnalizyTest {

    private static final String ONE_RANGE_LOG = "./data/859/one_range.log";
    private static final String TWO_RANGE_LOG = "./data/859/two_ranges.log";
    private static final String TARGET_FILE = "./data/859/unavailable.csv";

    @Test
    public void whenFileHasOneRangeOff() {
        Analizy analizy = new Analizy();
        analizy.unavailable(ONE_RANGE_LOG, TARGET_FILE);
        try (BufferedReader in = new BufferedReader(new FileReader(TARGET_FILE))) {
            List<String> lines = in.lines().toList();
            assertEquals(1L, lines.size());
            String[] timeStamps = lines.get(0).split(";");
            assertEquals("10:57:01", timeStamps[0]);
            assertEquals("11:02:02", timeStamps[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFileHasTwoRangesOff() {
        Analizy analizy = new Analizy();
        analizy.unavailable(TWO_RANGE_LOG, TARGET_FILE);
        try (BufferedReader in = new BufferedReader(new FileReader(TARGET_FILE))) {
            List<String> lines = in.lines().toList();
            assertEquals(2L, lines.size());
            String[] timeStamps1 = lines.get(0).split(";");
            assertEquals("10:57:01", timeStamps1[0]);
            assertEquals("10:59:01", timeStamps1[1]);
            String[] timeStamps2 = lines.get(1).split(";");
            assertEquals("11:01:02", timeStamps2[0]);
            assertEquals("11:02:02", timeStamps2[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
