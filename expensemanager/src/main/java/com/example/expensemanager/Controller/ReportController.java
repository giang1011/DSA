package com.example.expensemanager.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class ReportController {

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        // Dữ liệu mẫu cho biểu đồ
        Map<String, Double> categoryTotals = Map.of(
                "House", 5000.0,
                "Food", 3000.0,
                "Transportation", 2000.0,
                "Utilities", 1000.0,
                "Healthcare", 1500.0,
                "Entertainment", 800.0
        );

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setData(pieData);

        // Dữ liệu mẫu cho biểu đồ cột
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        incomeSeries.getData().add(new XYChart.Data<>("Jan", 4000));
        incomeSeries.getData().add(new XYChart.Data<>("Feb", 5000));
        incomeSeries.getData().add(new XYChart.Data<>("Mar", 4500));

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expense");
        expenseSeries.getData().add(new XYChart.Data<>("Jan", 2000));
        expenseSeries.getData().add(new XYChart.Data<>("Feb", 2500));
        expenseSeries.getData().add(new XYChart.Data<>("Mar", 3000));

        barChart.getData().addAll(incomeSeries, expenseSeries);
    }
}
