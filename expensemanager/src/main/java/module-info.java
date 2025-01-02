module com.example.expensemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.expensemanager to javafx.fxml;
    exports com.example.expensemanager;
    exports com.example.expensemanager.Controller;
    opens com.example.expensemanager.Controller to javafx.fxml;
    exports com.example.expensemanager.Entity;
    opens com.example.expensemanager.Entity to javafx.fxml;
}