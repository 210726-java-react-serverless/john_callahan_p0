package com.revature.projectZero.repositories;

import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;

// The purpose of this class is to work Students into the database;
// but only if they meet a certain set of standards.
// TODO Fix CrudRepository
public class SchoolRepository implements CrudRepository {

    @Override
    public Object findByID(int id) {
        return null;
    }

    @Override
    public Object save(Object newResource) {
        return null;
    }

    @Override
    public boolean update(Object updatedResource) {
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }

    @Override
    public Student findStudentByCredentials(String username, String password) {
        return null;
    }

    @Override
    public Student findStudentByUsername(String username) {
        return null;
    }

    @Override
    public Faculty findFacultyByCredentials(String username, String password) {
        return null;
    }

    @Override
    public Faculty findFacultyByUsername(String username) {
        return null;
    }
}
