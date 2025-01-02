package com.example.expensemanager.Entity;

import javafx.beans.property.*;

public class ExpenseRecord {

    private StringProperty expenseName;
    private StringProperty category;
    private DoubleProperty amount;
    private StringProperty expenseDate;

    public ExpenseRecord(String expenseName, double amount, String category) {
        this.expenseName = new SimpleStringProperty(expenseName);
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleDoubleProperty(amount);
        this.expenseDate = new SimpleStringProperty(java.time.LocalDate.now().toString()); // Setting current date
    }

    // Getter and Setter methods for each property

    public StringProperty expenseNameProperty() {
        return expenseName;
    }

    public String getExpenseName() {
        return expenseName.get();
    }

    public void setExpenseName(String expenseName) {
        this.expenseName.set(expenseName);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public StringProperty expenseDateProperty() {
        return expenseDate;
    }

    public String getExpenseDate() {
        return expenseDate.get();
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate.set(expenseDate);
    }
}
