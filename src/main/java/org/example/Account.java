package org.example;

/*
 * Author: Marvin Strasser
 * Version: 1.1
 * Date: 19/12/2024
 */
public class Account {
    int id;
    int balance;
    String name;
    int pin;

    public Account(int id, int balance, String name, int pin) {
        this.id = id;
        this.balance = balance;
        this.name = name;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
