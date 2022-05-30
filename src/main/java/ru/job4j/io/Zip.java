package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private final Path sourceDir;

    public Zip(Path sourceDir) {
        this.sourceDir = sourceDir;
    }

    public Path getSourceDir() {
        return sourceDir;
    }

    public void packFiles(List<File> sources, File target) throws IOException {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (File source : sources) {
                zip.putNextEntry(new ZipEntry(
                        getSourceDir().relativize(source.toPath()).toString()
                ));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                    zip.write(out.readAllBytes());
                    zip.closeEntry();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        Zip zip = new Zip(Path.of(argsName.get("d")));
        zip.validateParams(argsName);
        List<File> files = Search.search(zip.getSourceDir(),
                        path -> !path.toString().endsWith(argsName.get("e"))
                ).stream()
                .map(Path::toFile)
                .toList();
        zip.packFiles(files, new File(argsName.get("o")));
    }

    private void validateParams(ArgsName argsName) {
        Set<String> allArgsNames = argsName.allArgsNames();
        if (allArgsNames.size() != 3) {
            throw new IllegalArgumentException(
                    String.format("Неверное количество параметров запуска: %d %s%n%s",
                            allArgsNames.size(), allArgsNames, requiredParamsMessage()));
        }
        Set<String> requiredParams = Set.of("d", "e", "o");
        for (String argName : argsName.allArgsNames()) {
            if (!requiredParams.contains(argName)) {
                throw new IllegalArgumentException(
                        String.format("Неверное имя параметра '%s'%n%s", argName, requiredParamsMessage()));
            }
        }
        File rootFolder = new File(argsName.get("d"));
        if (!rootFolder.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("'%s' не является каталогом.", rootFolder.getAbsoluteFile())
            );
        }
        if (!rootFolder.exists()) {
            throw new IllegalArgumentException(
                    String.format("Каталога '%s' не существует.", rootFolder.getAbsoluteFile())
            );
        }
    }

    private String requiredParamsMessage() {
        return String.format("Программа должна принимать 3 параметра в произвольном порядке:%n"
                + "-d=[directory] - Путь к архивируемому каталогу%n"
                + "-e=[excluded] - Расширение файлов, не включаемых в архив, "
                + "в формате '.[расширение]', например: '.class'%n"
                + "-o=[output] - Путь к создаваемому архиву");
    }
}
