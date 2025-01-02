package com.example.expensemanager.Controller;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import com.example.expensemanager.Entity.ExpenseRecord;
import com.example.expensemanager.Entity.IncomeRecord;
import com.example.expensemanager.Service.IncomeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
public class IncomeController {

    @FXML
    private Label totalSpentLabel;

    @FXML
    private TableView<ExpenseRecord> recentExpenseTable;

    @FXML
    private TableColumn<ExpenseRecord, String> expenseNameColumn;

    @FXML
    private TableColumn<ExpenseRecord, String> expenseAmountColumn;
    @FXML
    private TableColumn<ExpenseRecord, String> expenseDateColumn;


    @FXML
    private Label totalIncomeLabel;

    @FXML
    private Label housingBudgetLabel;

    @FXML
    private Label foodBudgetLabel;

    @FXML
    private Label transportationBudgetLabel;

    @FXML
    private Label utilitiesBudgetLabel;

    @FXML
    private Label healthcareBudgetLabel;

    @FXML
    private Label entertainmentBudgetLabel;


    @FXML
    private Label totalBudgetLabel;

    private Map<String, Double> budgetMap = new HashMap<>();

    @FXML
    private TableView<IncomeRecord> recentIncomeTable;

    @FXML
    private TableColumn<IncomeRecord, String> amountColumn;

    @FXML
    private TableColumn<IncomeRecord, String> dateColumn;
    @FXML
    private BarChart<String, Number> budgetChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;


    @FXML
    private TableColumn<IncomeRecord, String> recordColumn;

    @FXML
    private TableColumn<ExpenseRecord, String> expenseCategoryColumn;

    private ObservableList<ExpenseRecord> expenseList = FXCollections.observableArrayList();

    private IncomeManager incomeManager;

    @FXML
    public void initialize() {
        amountColumn.setCellValueFactory(param -> param.getValue().amountProperty());
        dateColumn.setCellValueFactory(param -> param.getValue().dateProperty());
        recordColumn.setCellValueFactory(param -> param.getValue().recordProperty());

        expenseCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        expenseNameColumn.setCellValueFactory(cellData -> cellData.getValue().expenseNameProperty());
        expenseAmountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject().asString());
        expenseDateColumn.setCellValueFactory(cellData -> cellData.getValue().expenseDateProperty());
        recentExpenseTable.setItems(expenseList);


        incomeManager = new IncomeManager(recentIncomeTable, totalIncomeLabel);

        recentIncomeTable.getItems().addAll(
                new IncomeRecord("VND 1,000,000", "2024-12-29", "Salary")
        );

        incomeManager.addIncome("Salary", 1000000, "2024-12-29");

        // Khởi tạo các danh mục ngân sách mặc định
        budgetMap.put("House", 4700.0);
        budgetMap.put("Food", 1000.0);
        budgetMap.put("Transportation", 49460.0);
        budgetMap.put("Utilities", 4500.0);
        budgetMap.put("Healthcare", 1000.0);
        budgetMap.put("Entertainment", 2700.0);

        xAxis.setLabel("Category");
        yAxis.setLabel("Amount (VND)");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Budget Overview");

        series.getData().add(new XYChart.Data<>("Total Income", getTotalIncome()));
        series.getData().add(new XYChart.Data<>("Total Budget", getTotalBudget()));
        series.getData().add(new XYChart.Data<>("Total Spent", getTotalSpent()));

        budgetChart.getData().add(series);
    }

    @FXML
    private void handleAddIncome() {
        Dialog<IncomeRecord> dialog = new Dialog<>();
        dialog.setTitle("Add Income");
        dialog.setHeaderText("Enter Income Details");

        TextField incomeTypeInput = new TextField();
        TextField amountInput = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Income Type:"), 0, 0);
        grid.add(incomeTypeInput, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountInput, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String incomeType = incomeTypeInput.getText();
                    double amount = Double.parseDouble(amountInput.getText());
                    String date = LocalDate.now().toString();
                    return new IncomeRecord("VND " + amount, date, incomeType);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please enter a valid number for the amount.");
                    alert.showAndWait();
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(record -> {
            incomeManager.addIncome(record.getRecord(), Double.parseDouble(record.getAmount().substring(4)), record.getDate());
            updateBarChart();
        });

    }


    @FXML
    private void handleAddExpense() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Add Expense");

        VBox vbox = new VBox(10);
        vbox.getChildren().add(new Label("Select Category:"));

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("House", "Food", "Transportation", "Utilities", "Healthcare", "Entertainment");
        vbox.getChildren().add(categoryComboBox);

        vbox.getChildren().add(new Label("Expense Name:"));
        TextField expenseNameInput = new TextField();
        vbox.getChildren().add(expenseNameInput);

        vbox.getChildren().add(new Label("Amount:"));
        TextField amountInput = new TextField();
        vbox.getChildren().add(amountInput);

        Button addButton = new Button("Add Expense");
        addButton.setOnAction(e -> {
            String category = categoryComboBox.getValue();
            String expenseName = expenseNameInput.getText();
            String amountText = amountInput.getText();

            if (category == null || expenseName.isEmpty() || amountText.isEmpty()) {
                showErrorPopup("Please fill in all fields.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);

                double currentBudget = budgetMap.getOrDefault(category, 0.0);
                if (amount > currentBudget) {
                    showErrorPopup("Expense amount exceeds the available budget in the selected category.");
                    return;
                }

                ExpenseRecord expenseRecord = new ExpenseRecord(expenseName, amount, category);

                if (expenseList.contains(expenseRecord)) {
                    showErrorPopup("This expense has already been added.");
                    return;
                }

                expenseList.add(0, expenseRecord);

                if (!recentExpenseTable.getItems().contains(expenseRecord)) {
                    recentExpenseTable.getItems().add(0, expenseRecord);
                }

                double newBudget = currentBudget - amount;
                budgetMap.put(category, newBudget);

                updateCategoryBudgetLabel(category, newBudget);

                updateTotalSpent();
                dialogStage.close();
            } catch (NumberFormatException ex) {
                showErrorPopup("Please enter a valid number for the amount.");
            }
            updateBarChart();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> dialogStage.close());

        vbox.getChildren().addAll(addButton, cancelButton);

        Scene dialogScene = new Scene(vbox, 300, 250);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void updateTotalSpent() {
        double totalSpent = expenseList.stream().mapToDouble(ExpenseRecord::getAmount).sum();
        totalSpentLabel.setText("VND " + totalSpent);  // Update only the total spent label
    }

    private double getTotalIncome() {
        return incomeManager.getTotalIncome();
    }

    private double getTotalBudget() {
        return budgetMap.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    private double getTotalSpent() {
        return expenseList.stream().mapToDouble(ExpenseRecord::getAmount).sum();
    }





    private void updateCategoryBudgetLabel(String category, double newBudget) {
        switch (category) {
            case "House":
                housingBudgetLabel.setText("VND " + newBudget);
                break;
            case "Food":
                foodBudgetLabel.setText("VND " + newBudget);
                break;
            case "Transportation":
                transportationBudgetLabel.setText("VND " + newBudget);
                break;
            case "Utilities":
                utilitiesBudgetLabel.setText("VND " + newBudget);
                break;
            case "Healthcare":
                healthcareBudgetLabel.setText("VND " + newBudget);
                break;
            case "Entertainment":
                entertainmentBudgetLabel.setText("VND " + newBudget);
                break;
        }
    }

    private void updateTotalBudget() {
        double totalBudget = budgetMap.values().stream().mapToDouble(Double::doubleValue).sum();
        totalBudgetLabel.setText("VND " + totalBudget);
        updateBarChart();
    }

    @FXML
    private void handleHouseSummaryClick() {
        showExpensePopup("House");
    }

    @FXML
    private void handleFoodSummaryClick() {
        showExpensePopup("Food");
    }

    @FXML
    private void handleTransportationSummaryClick() {
        showExpensePopup("Transportation");
    }

    @FXML
    private void handleUtilitiesSummaryClick() {
        showExpensePopup("Utilities");
    }

    @FXML
    private void handleHealthcareSummaryClick() {
        showExpensePopup("Healthcare");
    }

    @FXML
    private void handleEntertainmentSummaryClick() {
        showExpensePopup("Entertainment");
    }

    private void showExpensePopup(String category) {
        // Create a new window for adding to the budget
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Add to Budget");

        TabPane tabPane = new TabPane();

        Tab addToBudgetTab = new Tab("Add to Budget");
        addToBudgetTab.setClosable(false);

        VBox budgetVBox = new VBox(10);
        Label budgetTitleLabel = new Label("Add Budget for " + category);
        budgetTitleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label budgetAmountLabel = new Label("Amount:");
        TextField budgetAmountInput = new TextField();
        budgetAmountInput.setPromptText("Enter the amount");

        Button addBudgetButton = new Button("Add to Budget");
        addBudgetButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        addBudgetButton.setOnAction(e -> {
            String budgetAmount = budgetAmountInput.getText();

            if (budgetAmount.isEmpty()) {
                showErrorPopup("Please fill in the amount field for budget.");
                return;
            }

            try {
                double budgetAmountValue = Double.parseDouble(budgetAmount); // Validate if it's a valid number

                double currentBudget = budgetMap.getOrDefault(category, 0.0);

                double newBudgetValue = currentBudget + budgetAmountValue;

                budgetMap.put(category, newBudgetValue);

                if (category.equals("House")) {
                    housingBudgetLabel.setText("VND " + newBudgetValue);
                } else if (category.equals("Food")) {
                    foodBudgetLabel.setText("VND " + newBudgetValue);
                } else if (category.equals("Entertainment")) {
                    entertainmentBudgetLabel.setText("VND " + newBudgetValue);
                } else if (category.equals("Utilities")) {
                    utilitiesBudgetLabel.setText("VND " + newBudgetValue);
                } else if (category.equals("Healthcare")) {
                    healthcareBudgetLabel.setText("VND " + newBudgetValue);
                } else if (category.equals("Transportation")) {
                    transportationBudgetLabel.setText("VND " + newBudgetValue);
                }

                double totalBudget = budgetMap.values().stream().mapToDouble(Double::doubleValue).sum();

                totalBudgetLabel.setText("VND " + totalBudget);

                showConfirmationPopup("Budget", "VND " + newBudgetValue, category, "Budget");

                dialogStage.close();
            } catch (NumberFormatException ex) {
                showErrorPopup("Please enter a valid amount for budget.");
            }
        });

        budgetVBox.getChildren().addAll(budgetTitleLabel, budgetAmountLabel, budgetAmountInput, addBudgetButton);
        addToBudgetTab.setContent(budgetVBox);

        tabPane.getTabs().add(addToBudgetTab);

        Scene dialogScene = new Scene(tabPane, 400, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationPopup(String name, String amount, String category, String action) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Action Successful");
        alert.setContentText(action + " for " + name + " " + category + ": " + amount);
        alert.showAndWait();
    }
    private void updateBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Budget Overview");


        series.getData().add(new XYChart.Data<>("Total Income", getTotalIncome()));
        series.getData().add(new XYChart.Data<>("Total Budget", getTotalBudget()));
        series.getData().add(new XYChart.Data<>("Total Spent", getTotalSpent()));

        budgetChart.getData().clear();
        budgetChart.getData().add(series);
    }
}
