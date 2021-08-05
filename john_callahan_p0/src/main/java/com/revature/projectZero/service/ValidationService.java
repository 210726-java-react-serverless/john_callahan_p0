package com.revature.projectZero.service;

import com.revature.projectZero.exceptions.InvalidRequestException;
import com.revature.projectZero.exceptions.ResourcePersistenceException;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.repositories.StudentRepository;

/**
    Takes in user data and validates it against a certain criteria. It is then passed to the service that puts
    it into MongoDB.
     */

public class ValidationService {

        private final StudentRepository studentRepo;
        public ValidationService(StudentRepository studentRepo){ this.studentRepo = studentRepo; }

        public Student register(Student newStudent) {
                if (!isUserValid(newStudent)) {
                        throw new InvalidRequestException("Invalid user data provided!");
                }

                if (studentRepo.findUserByUsername(newStudent.getUsername()) != null) {
                        throw new ResourcePersistenceException("Provided username is already taken!");
                }

                return (Student) (studentRepo.save(newStudent));
        }

        public Student login(String username, String password) {

                if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
                        throw new InvalidRequestException("Invalid user credentials provided!");
                }

                return studentRepo.findUserByCredentials(username, password);

        }

        public boolean isUserValid(Student user) {
                if (user == null) return false;
                if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
                if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
                if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
                if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
                return user.getPassword() != null && !user.getPassword().trim().equals("");
        }
}
