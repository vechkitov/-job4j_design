package ru.job4j.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditorDemo {
    private static final String TABLE_NAME = "test_table";
    private static final String COLUMN_NAME = "some_field";
    private static final String NES_COLUMN_NAME = "other_field";
    private static final String TYPE_TEXT = "text";

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        final var properties = new Properties();
        try (InputStream is = TableEditor.class.getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(is);
        }
        try (var te = new TableEditor(properties)) {
            te.createTable(TABLE_NAME);
            System.out.printf("1. Создана таблица %s:%n%s",
                    TABLE_NAME, getTableScheme(te.getConnection(), TABLE_NAME));
            te.addColumn(TABLE_NAME, COLUMN_NAME, TYPE_TEXT);
            System.out.printf("%n2. Добавлено поле %s:%n%s",
                    COLUMN_NAME, getTableScheme(te.getConnection(), TABLE_NAME));
            te.renameColumn(TABLE_NAME, COLUMN_NAME, NES_COLUMN_NAME);
            System.out.printf("%n3. Поле %s переименовано в %s:%n%s",
                    COLUMN_NAME, NES_COLUMN_NAME, getTableScheme(te.getConnection(), TABLE_NAME));
            te.dropColumn(TABLE_NAME, NES_COLUMN_NAME);
            System.out.printf("%n4. Поле %s удалено:%n%s",
                    NES_COLUMN_NAME, getTableScheme(te.getConnection(), TABLE_NAME));
            te.dropTable(TABLE_NAME);
        }
    }

    public static String getTableScheme(Connection connection, String tableName) throws SQLException {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format(
                        "%-15s|%-15s%n", metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }
}
