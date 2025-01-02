package com.example.expensemanager.Service;

import com.example.expensemanager.Entity.IncomeRecord;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class IncomeManager {
    private TableView<IncomeRecord> tableView;
    private Label totalIncomeLabel;
    private double totalIncome;

    public IncomeManager(TableView<IncomeRecord> tableView, Label totalIncomeLabel) {
        this.tableView = tableView;
        this.totalIncomeLabel = totalIncomeLabel;
        this.totalIncome = 0.0;
    }

    // Phương thức thêm thu nhập
    public void addIncome(String incomeType, double amount, String date) {
        totalIncome += amount;

        totalIncomeLabel.setText("VND " + String.format("%,.2f", totalIncome));

        IncomeRecord record = new IncomeRecord("VND " + amount, date, incomeType);
        tableView.getItems().add(0, record);
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void displayIncomeTree() {
        System.out.println("In-order Traversal of Income Tree:");

    }
}
