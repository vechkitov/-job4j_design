package ru.job4j.io;

import java.io.File;

public class Dir {

    public static void main(String[] args) {
        File file = new File("d:\\job4j\\projects\\job4j_design\\");
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsoluteFile()));
        }
        System.out.printf("size : %s%n", file.getTotalSpace());
        for (File f : file.listFiles()) {
            System.out.printf("%s : %s%n", f.getName(), f.isFile() ? String.valueOf(f.length()) : "dir");
        }
    }
}
