package com.example.expensemanager.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IncomeRecord {
    private StringProperty amount;
    private StringProperty date;
    private StringProperty record;

    public IncomeRecord(String amount, String date, String record) {
        this.amount = new SimpleStringProperty(amount);
        this.date = new SimpleStringProperty(date);
        this.record = new SimpleStringProperty(record);
    }

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getRecord() {
        return record.get();
    }

    public StringProperty recordProperty() {
        return record;
    }
}
