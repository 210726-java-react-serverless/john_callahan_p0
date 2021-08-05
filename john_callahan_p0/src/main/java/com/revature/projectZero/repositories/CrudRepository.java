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
    F save(F newResource);
    boolean update(F updatedResource);
    boolean deleteByID(int id);

    public Student findStudentByCredentials(String username, String password);

    public Student findStudentByUsername(String username);

    public Faculty findFacultyByCredentials(String username, String password);

    public Faculty findFacultyByUsername(String username);
}
