package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        URL resourceUrl = ConnectionDemo.class.getClassLoader().getResource("app.properties");
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Ресурс 'app.properties' не найден.");
        }
        Config config = new Config(resourceUrl.getFile());
        config.load();
        Class.forName(config.value("db.driver"));
        try (Connection connection = DriverManager.getConnection(
                config.value("db.url"), config.value("db.login"), config.value("db.password"))) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
        }
    }
}
