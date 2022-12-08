package ru.job4j.ood.srp.notsrp;

/**
 * Модель данных с лишней логикой.
 * Методы 'putMoney' и 'withdrawMoney' нарушают SRP, т.к. основная функциональность класса: модель данных, а
 * эти методы выполняют бизнес-логику, не относящуюся к основной функциональности.
 */
public class Account {
    private String requisite;
    private double balance;

    public Account(String requisite, double balance) {
        this.requisite = requisite;
        this.balance = balance;
    }

    public String getRequisite() {
        return requisite;
    }

    public double getBalance() {
        return balance;
    }

    public void putMoney(double sum) {
        balance += sum;
    }

    public void withdrawMoney(double sum) {
        balance -= sum;
    }
}
