package com.revature.projectZero.pojos;

import java.util.Objects;

public class Course {
    private String classID;
    private String name;
    private String id;
    private String desc;
    private String teacher;
    boolean isOpen;

    public Course(String classID, String name, String id, String desc, String teacher, boolean isOpen) { }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return isOpen == course.isOpen && Objects.equals(classID, course.classID) && Objects.equals(name, course.name) && Objects.equals(id, course.id) && Objects.equals(desc, course.desc) && Objects.equals(teacher, course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID, name, id, desc, teacher, isOpen);
    }

    @Override
    public String toString() {
        return "Course{" +
                "classID='" + classID + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", teacher='" + teacher + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
