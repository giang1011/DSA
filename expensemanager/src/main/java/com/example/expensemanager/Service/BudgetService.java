package com.example.expensemanager.Service;

import java.util.HashMap;
import java.util.Map;

public class BudgetService {
    private final Map<String, Double> budgetMap = new HashMap<>();

    public BudgetService() {
        // Khởi tạo ngân sách mặc định
        budgetMap.put("House", 4700.0);
        budgetMap.put("Food", 1000.0);
        budgetMap.put("Transportation", 49460.0);
        budgetMap.put("Utilities", 4500.0);
        budgetMap.put("Healthcare", 1000.0);
        budgetMap.put("Entertainment", 2700.0);
    }

    public double getBudget(String category) {
        return budgetMap.getOrDefault(category, 0.0);
    }

    public void addToBudget(String category, double amount) {
        budgetMap.put(category, getBudget(category) + amount);
    }

    public boolean addToExpense(String category, double amount) {
        double currentBudget = getBudget(category);
        if (currentBudget >= amount) {
            budgetMap.put(category, currentBudget - amount);
            return true;
        }
        return false;
    }

    public double getTotalBudget() {
        return budgetMap.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
