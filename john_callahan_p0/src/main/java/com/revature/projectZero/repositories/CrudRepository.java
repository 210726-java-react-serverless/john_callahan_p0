package com.revature.projectZero.repositories;

/*
        About Generics:
        Generics are a means for Java to work without explicitly knowing what will be passed to it,
        meaning the same code might work for several operations.
        I believe this code is one great example of this, since it can be passed anything as 'F'.
 */

import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;

public interface CrudRepository<F> {
    F findByID(int id);
    F save(F newStudent);
    boolean update(F updatedResource);
    boolean deleteByID(int id);

    Faculty findFacultyByCredentials(String username, String password);
    Student findStudentByUsername(String username);
    Student findStudentByCredentials(String username, int password);

}
