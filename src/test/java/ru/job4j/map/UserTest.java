package ru.job4j.map;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class UserTest {

    @Test
    public void test() {
        Calendar birthday = new GregorianCalendar(2000, 2, 10);
        User user1 = new User("Petr", 3, birthday);
        User user2 = new User("Petr", 3, birthday);
        Object obj1 = new Object();
        Object obj2 = new Object();
        Map<User, Object> users = new HashMap<>();
        users.put(user1, obj1);
        users.put(user2, obj2);
        System.out.println("obj1: " + obj1);
        System.out.println("obj2: " + obj2);
        users.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
