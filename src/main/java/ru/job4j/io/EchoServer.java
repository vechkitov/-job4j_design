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
                    if (str.contains("/?msg=Bye")) {
                        server.close();
                    }
                    while (str != null && !str.isEmpty()) {
                        System.out.println(str);
                        str = in.readLine();
                    }
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    out.flush();
                }
            }
        }
    }
}
