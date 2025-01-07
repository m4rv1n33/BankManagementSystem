package org.example;
import jakarta.persistence.*;
/*
 * Author: Marvin Strasser
 * Version: 1.1
 * Date: 19/12/2024
 */
@Entity
public class Account {

    @Id
    private int id;
    private double balance;
    private String name;
    private int pin;

    public Account(double balance, int id, String name, int pin) {
        this.balance = balance;
        this.id = id;
        this.name = name;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
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