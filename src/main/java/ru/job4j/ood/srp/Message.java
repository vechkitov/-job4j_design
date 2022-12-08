package ru.job4j.ood.srp;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Три примера нарушения принципа SRP:
 * 1. В классе более одной функциональности: 1) хранение данных; 2) запись своих данных в БД (метод 'save').
 * Т.о., существует более 1-й причины для изменения класса: 1) изменить набор данных; 2) изменить логику работы с БД.
 * Функционал сохранения сообщения должен быть в другом классе.
 * <p>
 * 2. В методе 'save' более одной функциональности: 1) инициализация 'Connection'; 2) сохранение данных.
 * Т.о., существует более 1-й причины для изменения метода: 1) изменить алгоритм инициализации; 2) изменить
 * логику сохранения данных.
 * Инициализация 'Connection' должна быть вынесена из метода 'save'.
 * <p>
 * 3. В методе 'getSendDateByString' более одной функциональности: 1) инициализация
 * форматтера; 2) форматирование данных; 3) предоставление данных пользователю.
 * Т.о., существует более 1-й причины для изменения метода: 1) изменить используемый форматтер; 2) изменить
 * логику форматирования данных.
 * Форматтер мы должны получать извне. Логика форматирования должна быть в отдельном методе.
 */
public class Message {
    private LocalDateTime sendDate;
    private String text;

    public void save() {
        try (var cn = DriverManager.getConnection(
                "url", "login", "password")) {
            try (var ps = cn.prepareStatement(
                    "insert into message(send_date, text) values(?,?);")) {
                ps.setTimestamp(1, Timestamp.valueOf(sendDate));
                ps.setString(2, text);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getSendDateByString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return sendDate.format(df) + "г.";
    }
}
