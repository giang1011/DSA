package com.example.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentController {
    @FXML
    private ComboBox<String> genderField;

    @FXML
    private TextField studentIdField, nameField, dobField, ageField, scoreField;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colStudentId, colName, colDob, colGender;
    @FXML
    private TableColumn<Student, Integer> colAge;
    @FXML
    private TableColumn<Student, Double> colScore;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private int nextId = 1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        studentTable.setItems(studentList);

        genderField.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
    }

    @FXML
    public void addStudent() {
        try {
            // Sử dụng nextId làm ID tự động
            int id = nextId++;
            String studentId = studentIdField.getText();
            String name = nameField.getText();
            String dob = dobField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getValue();
            double score = Double.parseDouble(scoreField.getText());

            if (score < 0 || score > 10) {
                showAlert("Invalid Score", "Score must be between 0 and 10.");
                return;
            }

            Student student = new Student(id, studentId, name, dob, age, gender, score);
            studentList.add(student);

            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numerical values for Age and Score.");
        }
    }

    @FXML
    public void sortStudentsWithBubbleSort() {
        if (studentList.isEmpty()) {
            showAlert("No Students", "The student list is empty. Cannot sort.");
            return;
        }
        for (int i = 0; i < studentList.size() - 1; i++) {
            for (int j = 0; j < studentList.size() - i - 1; j++) {
                if (studentList.get(j).getScore() > studentList.get(j + 1).getScore()) {
                    Student temp = studentList.get(j);
                    studentList.set(j, studentList.get(j + 1));
                    studentList.set(j + 1, temp);
                }
            }
        }
        studentTable.refresh();
    }

    @FXML
    public void sortStudentsWithInsertionSort() {
        if (studentList.isEmpty()) {
            showAlert("No Students", "The student list is empty. Cannot sort.");
            return;
        }
        for (int i = 1; i < studentList.size(); i++) {
            Student key = studentList.get(i);
            int j = i - 1;
            while (j >= 0 && studentList.get(j).getScore() > key.getScore()) {
                studentList.set(j + 1, studentList.get(j));
                j--;
            }
            studentList.set(j + 1, key);
        }
        studentTable.refresh();
    }

    @FXML
    public void sortStudentsWithSelectionSort() {
        if (studentList.isEmpty()) {
            showAlert("No Students", "The student list is empty. Cannot sort.");
            return;
        }
        for (int i = 0; i < studentList.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < studentList.size(); j++) {
                if (studentList.get(j).getScore() < studentList.get(minIndex).getScore()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Student temp = studentList.get(i);
                studentList.set(i, studentList.get(minIndex));
                studentList.set(minIndex, temp);
            }
        }
        studentTable.refresh();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        studentIdField.clear();
        nameField.clear();
        dobField.clear();
        ageField.clear();
        scoreField.clear();
        genderField.setValue(null);
    }
}
