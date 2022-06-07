package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                try (Socket socket = server.accept()) {
                    OutputStream out = socket.getOutputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str = in.readLine();
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    String paramValue = str.split("\s")[1]
                            .replace("/?msg=", "");
                    if ("Hello".equals(paramValue)) {
                        out.write("Hello".getBytes());
                    } else if ("Exit".equals(paramValue)) {
                        out.write("Bye".getBytes());
                        server.close();
                    } else {
                        out.write("What".getBytes());
                    }
                    while (str != null && !str.isEmpty()) {
                        System.out.println(str);
                        str = in.readLine();
                    }
                    out.flush();
                }
            }
        }
    }
}
