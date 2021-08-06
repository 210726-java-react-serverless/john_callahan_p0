package com.revature.projectZero.service;

import com.revature.projectZero.util.exceptions.InvalidRequestException;
import com.revature.projectZero.util.exceptions.ResourcePersistenceException;
import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.repositories.SchoolRepository;

/**
    Takes in user data and validates it against a certain criteria. It is then passed to the service that puts
    it into MongoDB.
     */

public class ValidationService {

        // TODO: Get Validation Services working!

        private final SchoolRepository schoolRepo;
        public ValidationService(SchoolRepository studentRepo){ this.schoolRepo = studentRepo; }

        public Student register(Student newStudent) throws Exception {
                if (!isUserValid(newStudent)) {
                        throw new InvalidRequestException("Invalid user data provided!");
                }

                if (schoolRepo.findStudentByUsername(newStudent.getUsername()) != null) {
                        throw new ResourcePersistenceException("Provided username is already taken!");
                }

                return schoolRepo.save(newStudent);
        }

        public Student login(String username, String password) throws Exception {
                if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
                        throw new InvalidRequestException("Invalid user credentials provided!");
                }

                return schoolRepo.findStudentByCredentials(username, password);
        }

        public Faculty facLogin(String username, String password) throws Exception {
                if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
                        throw new InvalidRequestException("Invalid user credentials provided!");
                }

                return schoolRepo.findFacultyByCredentials(username, password);

        }


        // This verifies that students are valid and fit to be placed in the system.
        public boolean isUserValid(Student user) {
                if (user == null) return false;
                if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
                if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
                if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
                if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
                this.isUserUnique(user.getUsername());
                return user.getPassword() != null && !user.getPassword().trim().equals("");
        }

        public boolean isUserUnique(String username) {
                return schoolRepo.findStudentByUsername(username) == null;
        }
}
