package com.example.expensemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Expense Manager");

        Screen screen = Screen.getPrimary();
        primaryStage.setWidth(screen.getVisualBounds().getWidth());
        primaryStage.setHeight(screen.getVisualBounds().getHeight());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
