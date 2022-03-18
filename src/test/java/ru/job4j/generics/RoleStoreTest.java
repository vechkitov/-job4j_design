package ru.job4j.generics;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RoleStoreTest {

    @Test
    public void whenAddAndFindThenRoleNameIsAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        assertThat(store.findById("1").getRoleName(), is("admin"));
    }

    @Test
    public void whenAddAndFindThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        assertNull(store.findById("2"));
    }

    @Test
    public void whenAddDuplicateAndFindRoleNameIsAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        store.add(new Role("1", "user"));
        assertThat(store.findById("1").getRoleName(), is("admin"));
    }

    @Test
    public void whenReplaceThenRoleNameIsUser() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        store.replace("1", new Role("2", "user"));
        assertThat(store.findById("1").getRoleName(), is("user"));
    }

    @Test
    public void whenNoReplaceRoleThenNoChangeRoleName() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        store.replace("2", new Role("2", "user"));
        assertThat(store.findById("1").getRoleName(), is("admin"));
    }

    @Test
    public void whenReplaceNullThenReturnTrue() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        store.replace("1", null);
        assertTrue(store.replace("1", new Role("2", "user")));
    }

    @Test
    public void whenDeleteRoleThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        store.delete("1");
        assertNull(store.findById("1"));
    }

    @Test
    public void whenNoDeleteRoleThenRoleNameIsAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "admin"));
        store.delete("2");
        assertThat(store.findById("1").getRoleName(), is("admin"));
    }
}
