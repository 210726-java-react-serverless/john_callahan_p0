package com.revature.projectZero.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Student {

    private String studentID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    // public empty constructor is needed to retrieve the POJO
    public Student(){}

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Student(String username, String password, String firstName, String lastName, String email) {
        this(username, password);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String username, String password, String firstName, String lastName, String email, int id) {
        this(firstName, lastName, email, username, password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentID, student.studentID) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email) && Objects.equals(username, student.username) && Objects.equals(password, student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, firstName, lastName, email, username, password);
    }

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
