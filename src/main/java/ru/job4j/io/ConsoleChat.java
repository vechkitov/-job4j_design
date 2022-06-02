package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private static final Set<String> KEY_WORDS = Set.of(OUT, STOP, CONTINUE);
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() throws IOException {
        List<String> answers = readPhrases();
        List<String> log = new ArrayList<>();
        Random random = new Random();
        String prompt = String.format("Введите любое слово ('%s' - выход из программы, '%s' - приостановить "
                + "бота, '%s' - активировать бота):", OUT, STOP, CONTINUE);
        boolean isRun = true;
        String msg = "";
        while (!OUT.equals(msg)) {
            System.out.println(prompt);
            msg = new Scanner(System.in).nextLine();
            log.add(msg);
            if (KEY_WORDS.contains(msg)) {
                isRun = switch (msg) {
                    case STOP -> false;
                    case CONTINUE -> true;
                    default -> isRun;
                };
            } else if (isRun) {
                String answer = answers.get(random.nextInt(answers.size()));
                System.out.println(answer);
                log.add(answer);
            }
        }
        saveLog(log);
    }

    private List<String> readPhrases() throws IOException {
        try (final var in = new BufferedReader(new FileReader(botAnswers, Charset.forName("windows-1251")))) {
            return in.lines().collect(Collectors.toList());
        }
    }

    private void saveLog(List<String> log) throws IOException {
        try (final var out = new PrintWriter(path, Charset.forName("windows-1251"))) {
            log.forEach(out::println);
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleChat cc = new ConsoleChat("d:/tmp/log.txt", "d:/tmp/answers.txt");
        cc.run();
    }
}
