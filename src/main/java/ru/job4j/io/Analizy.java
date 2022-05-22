package ru.job4j.io;

import java.io.*;
import java.util.Set;

public class Analizy {

    private static final Set<String> OFF_STATUSES = Set.of("400", "500");

    public void unavailable(String source, String target) {
        try (BufferedReader in = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(
                     new BufferedWriter(new FileWriter(target)))
        ) {
            boolean isOn = true;
            String lineOut = "";
            String lineIn;
            while ((lineIn = in.readLine()) != null) {
                String[] connectData = lineIn.split("\\s");
                if (isOn && OFF_STATUSES.contains(connectData[0])) {
                    isOn = false;
                    lineOut = connectData[1];
                } else if (!isOn && !OFF_STATUSES.contains(connectData[0])) {
                    isOn = true;
                    out.printf("%s;%s;%n", lineOut, connectData[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
