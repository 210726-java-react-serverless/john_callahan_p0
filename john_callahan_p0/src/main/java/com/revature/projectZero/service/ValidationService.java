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

        private boolean isValid = false;
        private Student authStudent;
        private Faculty authFac;

        public void register(Student newStudent) throws Exception {
                if (!isUserValid(newStudent)) {
                        throw new InvalidRequestException("Invalid user data provided!");
                }

                if (schoolRepo.findStudentByUsername(newStudent.getUsername()) != null) {
                        throw new ResourcePersistenceException("Provided username is already taken!");
                }

                schoolRepo.save(newStudent);
        }

        // Wipes user data and sets the session to an invalid one.
        public void logout() {
                this.isValid = false;
                this.authStudent = null;
                this.authFac = null;
        }

        public Student login(String username, int hashPass) throws Exception {
                if (username == null || username.trim().equals("")) {
                        throw new InvalidRequestException("Invalid user credentials provided!");
                }

                // This ensures that the session is marked 'valid'.
                this.isValid = true;

                this.authStudent = schoolRepo.findStudentByCredentials(username, hashPass);
                return this.authStudent;
        }

        // Sends Student data to the requested location
        public Student getStudent() {
                if(this.authStudent != null || this.isValid) {
                        return this.authStudent;
                } else {
                        return null;
                }
        }

        public Faculty facLogin(String username, int hashPass) throws Exception {
                if (username == null || username.trim().equals("")) {
                        throw new InvalidRequestException("Invalid user credentials provided!");
                }

                this.authFac = schoolRepo.findFacultyByCredentials(username, hashPass);
                return this.authFac;
        }

        // Sends faculty user data to the requested location
        public Faculty getAuthFac() {
                if(this.authFac != null || this.isValid) {
                        return this.authFac;
                } else {
                        return null;
                }
        }


        // This verifies that students are valid and fit to be placed in the system.
        public boolean isUserValid(Student user) {
                if (user == null) return false;
                if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
                if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
                if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
                if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
                this.isUserUnique(user.getUsername());
                return true;
        }

        public boolean isUserUnique(String username) {
                return schoolRepo.findStudentByUsername(username) == null;
        }

        public boolean isEmailUnique(String email) {
                return schoolRepo.findStudentByEmail(email) == null;
        }
}
