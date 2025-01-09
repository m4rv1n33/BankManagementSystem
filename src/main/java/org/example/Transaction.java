package org.example;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int accountId;
    private String type;
    private double amount;
    private Timestamp timestamp;

    // Getters and setters
}
