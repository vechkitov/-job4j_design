package ru.job4j.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TableEditor implements AutoCloseable {

    private Connection connection;
    private Properties properties;

    public TableEditor(Properties properties) throws IOException, SQLException, ClassNotFoundException {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws IOException, ClassNotFoundException, SQLException {
        properties.load(
                TableEditor.class.getClassLoader().getResourceAsStream("app.properties"));
        Class.forName(properties.getProperty("db.driver"));
        connection = DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.login"),
                properties.getProperty("db.password"));
    }

    /**
     * Создает пустую таблицу без столбцов с указанным именем
     *
     * @param tableName имя таблицы
     */
    public void createTable(String tableName) throws SQLException {
        executeSql(String.format("create table if not exists %s();", tableName));
    }

    /**
     * Удаляет таблицу по указанному имени
     *
     * @param tableName имя удаляемой таблицы
     */
    public void dropTable(String tableName) throws SQLException {
        executeSql(String.format("drop table %s;", tableName));
    }

    /**
     * Добавляет поле в таблицу
     *
     * @param tableName  имя таблицы
     * @param columnName имя добавляемого поля
     * @param type       тип добавляемого поля
     * @throws SQLException
     */
    public void addColumn(String tableName, String columnName, String type) throws SQLException {
        executeSql(String.format("alter table %s add column %s %s;", tableName, columnName, type));
    }

    /**
     * Удаляет поле из таблицы
     *
     * @param tableName  имя таблицы
     * @param columnName имя поля
     */
    public void dropColumn(String tableName, String columnName) throws SQLException {
        executeSql(String.format("alter table %s drop column %s;", tableName, columnName));
    }

    /**
     * Переименовывает поле
     *
     * @param tableName     имя таблицы
     * @param columnName    имя поля
     * @param newColumnName новое имя поля
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) throws SQLException {
        executeSql(String.format("alter table %s rename column %s to %s;", tableName, columnName, newColumnName));
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void executeSql(String sql) throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
