package ru.job4j.question;

import java.util.*;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int changed = 0;
        Map<Integer, User> curUsers = new HashMap<>(current.size());
        current.stream()
                .forEach(user -> curUsers.put(user.getId(), user));
        /* В тестах используются немутабельные Set'ы, в которых отсутствует метод remove(),
        * поэтому обернул входящий Set в HashSet */
        Set<User> prevUsers = new HashSet<>(previous);
        Iterator<User> prevIterator = prevUsers.iterator();
        while (prevIterator.hasNext()) {
            User prevUser = prevIterator.next();
            int id = prevUser.getId();
            if (curUsers.containsKey(id)) {
                if (!prevUser.equals(curUsers.get(id))) {
                    changed++;
                }
                prevIterator.remove();
                curUsers.remove(id);
            }
        }
        return new Info(curUsers.size(), changed, prevUsers.size());
    }
}
