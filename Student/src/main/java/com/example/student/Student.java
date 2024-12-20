package com.example.student;

public class Student {
    private int id;
    private String studentId;
    private String name;
    private String dob;
    private int age;
    private String gender;
    private double score;

    public Student() {;}
    public Student(int id, String studentId, String name, String dob, int age, String gender, double score) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
