package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnalizyTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenFileHasOneRangeOff() throws IOException {
        File source = folder.newFile("source.log");
        try (PrintWriter out = new PrintWriter(source)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("500 10:59:01");
            out.println("400 11:01:02");
            out.println("200 11:02:02");
        }
        File target = folder.newFile("unavailable.csv");
        Analizy analizy = new Analizy();
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            List<String> lines = in.lines().toList();
            assertEquals(1L, lines.size());
            String[] timeStamps = lines.get(0).split(";");
            assertEquals("10:57:01", timeStamps[0]);
            assertEquals("11:02:02", timeStamps[1]);
        }
    }

    @Test
    public void whenFileHasTwoRangesOff() throws IOException {
        File source = folder.newFile("source.log");
        try (PrintWriter out = new PrintWriter(source)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("200 10:59:01");
            out.println("500 11:01:02");
            out.println("200 11:02:02");
        }
        File target = folder.newFile("unavailable.csv");
        Analizy analizy = new Analizy();
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            List<String> lines = in.lines().toList();
            assertEquals(2L, lines.size());
            String[] timeStamps1 = lines.get(0).split(";");
            assertEquals("10:57:01", timeStamps1[0]);
            assertEquals("10:59:01", timeStamps1[1]);
            String[] timeStamps2 = lines.get(1).split(";");
            assertEquals("11:01:02", timeStamps2[0]);
            assertEquals("11:02:02", timeStamps2[1]);
        }
    }
}
