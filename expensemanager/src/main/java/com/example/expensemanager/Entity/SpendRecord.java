package com.example.expensemanager.Entity;

import java.time.LocalDate;

public class SpendRecord {
    private String category;
    private String expenseName;
    private double amount;
    private LocalDate date;

    // Constructor
    public SpendRecord(String category, String expenseName, double amount, LocalDate date) {
        this.category = category;
        this.expenseName = expenseName;
        this.amount = amount;
        this.date = date;
    }

    // Getters and setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // To String method for easy display
    @Override
    public String toString() {
        return "SpendRecord{" +
                "category='" + category + '\'' +
                ", expenseName='" + expenseName + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}

