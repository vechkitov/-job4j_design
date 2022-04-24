package ru.job4j.map;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class UserTest {

    @Test
    public void whenDoNotOverrideEqualsAndHashCode() {
        Calendar birthday = new GregorianCalendar(2000, 2, 10);
        User user1 = new User("Petr", 3, birthday);
        User user2 = new User("Petr", 3, birthday);
        Map<User, Object> users = new HashMap<>();
        users.put(user1, new Object());
        users.put(user2, new Object());
        users.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
