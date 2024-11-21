package com.example.expensemanager;
import java.time.LocalDate;

public class Transaction {
    private int id;
    private String inout;
    private String cause;
    private Double amount;
    private String description;
    private LocalDate date;
    private int userId;

    public Transaction( String inout, String cause, Double amount, String description, LocalDate date, int userId) {

        this.inout = inout;
        this.cause = cause;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }
    public Transaction( int id, String inout, String cause, Double amount, String description, LocalDate date, int userId) {
        this.id = id;
        this.inout = inout;
        this.cause = cause;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public String getInout() {
        return inout;
    }

    public String getCause() {
        return cause;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setInout(String inout) {
        this.inout = inout;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

}