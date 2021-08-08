package com.revature.projectZero.service;

import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.pojos.Enrolled;
import com.revature.projectZero.util.exceptions.InvalidRequestException;
import com.revature.projectZero.util.exceptions.ResourcePersistenceException;
import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.repositories.SchoolRepository;
import java.util.List;

/**
    Takes in user data and validates it against a certain criteria. It is then passed to the service that puts
    it into MongoDB.
     */

public class ValidationService {

        private final SchoolRepository schoolRepo;
        public ValidationService(SchoolRepository studentRepo){ this.schoolRepo = studentRepo; }
        private boolean isValid = true;
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

        // This will attempt to persist a created course to the courses database.
        public void createCourse(Course newCourse) {

                try {
                        newCourse.setTeacher(this.authFac.getLastName());
                        System.out.println(newCourse);
                        // TODO: Run this collected object against some criteria!
                        schoolRepo.newCourse(newCourse);
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                }
        }

        // This enrolls a student into a course, grafting their username to it,
        // and placing it within the separate 'enrolled' database.
        public void enroll(String id) {
                Course course = schoolRepo.findCourseByID(id);

                Enrolled enrollIn = new Enrolled(course.getClassID(), this.authStudent.getUsername(), course.getName() ,
                        course.getId(),  course.getDesc(), course.getTeacher());
                try {
                        schoolRepo.enroll(enrollIn);
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                }
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

        // This returns one class at the user's request (for the purposes of an update function)
        public Course getCourseByID(String id) { return schoolRepo.findCourseByID(id); }

        // This returns all classes that are open for enrollment.
        public List<Course> getOpenClasses() { return schoolRepo.findCourseByOpen(); }

        // This returns all classes associated with a student username.
        public List<Enrolled> getMyCourses() { return schoolRepo.findEnrolledByUsername(this.authStudent.getUsername()); }

        // This fetches the list of classes associated with a certain teacher name.
        public List<Course> getTeacherClasses() { return schoolRepo.findCourseByTeacher(this.authFac.getLastName()); }

        public void updateCourse(Course newCourse, String id) {
                try {
                        String teacher = this.authFac.getLastName();
                        schoolRepo.updateCourse(newCourse, id, teacher);
                } catch (Exception e) {
                        System.out.println("Updated course not persisted! " + e.getMessage());
                }
        }

        public void deleteCourse(String id) {
                try {
                        // TODO: Validate this input!
                        schoolRepo.deleteCourse(id);
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                }
        }

        // TODO: Implement some verification for Teacher-submitted courses!

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
