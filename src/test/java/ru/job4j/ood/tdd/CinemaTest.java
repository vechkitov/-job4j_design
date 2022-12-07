package ru.job4j.ood.tdd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
public class CinemaTest {

    @Test
    public void whenBuyThenGetTicket() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        Ticket ticket = cinema.buy(account, 1, 1, date);
        assertThat(ticket).isEqualTo(new Ticket3D());
    }

    @Test
    public void whenAddSessionThenItExistsBetweenAllSessions() {
        Cinema cinema = new Cinema3D();
        Session session = new Session3D();
        cinema.add(session);
        List<Session> sessions = cinema.find(s -> true);
        assertThat(sessions).contains(session);
    }

    @Test
    public void whenBuyOnInvalidRowThenGetException() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        assertThatThrownBy(() -> cinema.buy(account, -1, 1, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenFindByNullThenGetException() {
        assertThatThrownBy(() -> new Cinema3D().find(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSessionIsNotExistsThenGetEmptyList() {
        Cinema cinema = new Cinema3D();
        Session session1 = new Session3D();
        Session session2 = new Session3D();
        cinema.add(session1);
        List<Session> sessions = cinema.find(s -> false);
        assertThat(sessions).isEmpty();
    }

    @Test
    public void whenAddNullSessionThenGetException() {
        assertThatThrownBy(() -> new Cinema3D().add(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBuyOnNullAccountThenGetException() {
        assertThatThrownBy(() -> new Cinema3D().buy(null, 1, 1, Calendar.getInstance()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBuyOnInvalidColumnThenGetException() {
        assertThatThrownBy(() -> new Cinema3D().buy(new AccountCinema(), 1, -1, Calendar.getInstance()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSessionDateGreatThenCurrentDateThenGetException() {
        Calendar date = Calendar.getInstance();
        date.set(9999, Calendar.DECEMBER, 31);
        assertThatThrownBy(() -> new Cinema3D().buy(new AccountCinema(), 1, 1, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBuyTicketOnTheSameSeatThenGetException() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        cinema.buy(account, 1, 1, date);
        assertThatThrownBy(() -> cinema.buy(account, 1, 1, date))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
