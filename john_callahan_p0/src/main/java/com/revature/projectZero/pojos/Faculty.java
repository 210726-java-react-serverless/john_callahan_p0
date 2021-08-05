package com.revature.projectZero.pojos;

import java.util.Objects;

public class Faculty {

    private int teacherID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    // public empty constructor is needed to retrieve the POJO
    public Faculty(){}

    public Faculty(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Faculty(String username, String password, String firstName, String lastName, String email) {
        this(username, password);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Faculty(String username, String password, String firstName, String lastName, String email, int id) {
        this(firstName, lastName, email, username, password);
        this.teacherID = id;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
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
        Faculty student = (Faculty) o;
        return teacherID == student.teacherID && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email) && Objects.equals(username, student.username) && Objects.equals(password, student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherID, firstName, lastName, email, username, password);
    }
}
