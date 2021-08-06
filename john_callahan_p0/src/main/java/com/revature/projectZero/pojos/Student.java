package com.revature.projectZero.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Student {

    private String studentID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private int hashPass;

    // public empty constructor is needed to retrieve the POJO
    public Student(){}

    public Student(String username, int password, String firstName, String lastName, String email) {
        this.username = username;
        this.hashPass = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHashPass() {
        return hashPass;
    }

    public void setHashPass(int hashPass) {
        this.hashPass = hashPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentID, student.studentID) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email) && Objects.equals(username, student.username) && Objects.equals(hashPass, student.hashPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, firstName, lastName, email, username, hashPass);
    }

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + hashPass + '\'' +
                '}';
    }
}
