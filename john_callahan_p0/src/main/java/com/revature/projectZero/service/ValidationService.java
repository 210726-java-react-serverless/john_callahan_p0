package com.revature.projectZero.service;

import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.pojos.Enrolled;
import com.revature.projectZero.util.exceptions.InvalidRequestException;
import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.repositories.SchoolRepository;
import com.revature.projectZero.util.exceptions.ResourcePersistenceException;

import java.util.List;

/**
 *  A service-layer middleman that validates input before passing it to the database class responsible for
 *  placing it into the database. It also does Session Tracking.
 */

public class ValidationService {

        private final SchoolRepository schoolRepo;

        public ValidationService(SchoolRepository studentRepo){ this.schoolRepo = studentRepo; }

        private boolean isValid = true;
        private Student authStudent;
        private Faculty authFac;


        public void register(Student newStudent) {

                if (!isUserValid(newStudent)) {
                        throw new InvalidRequestException("Invalid user data provided!");
                }

                schoolRepo.save(newStudent);
        }

        // This will attempt to persist a created course to the courses database.
        public void createCourse(Course newCourse) {

                newCourse.setTeacher(this.authFac.getLastName());

                if (isCourseValid(newCourse)) {
                        schoolRepo.newCourse(newCourse);
                }
        }

        // This enrolls a student into a course, grafting their username to it,
        // and placing it within the separate 'enrolled' database.
        public void enroll(Course selectedCourse) {
                if (isCourseValid(selectedCourse)) {
                        Enrolled enrollIn = new Enrolled(this.authStudent.getUsername(), selectedCourse.getName(),
                                selectedCourse.getClassID(), selectedCourse.getDesc(), selectedCourse.getTeacher());
                        schoolRepo.enroll(enrollIn);
                }
        }

        // Wipes user data and sets the session to an invalid one.
        public void logout() {
                this.isValid = false;
                this.authStudent = null;
                this.authFac = null;
        }

        public void setAuthStudent(Student authStudent) {
                this.authStudent = authStudent;
        }

        public void setAuthFac(Faculty authFac) {
                this.authFac = authFac;
        }

        public Student login(String username, int hashPass) {
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

        public Faculty facLogin(String username, int hashPass) {
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
                if (isCourseValid(newCourse)) {
                        String teacher = this.authFac.getLastName();
                        schoolRepo.updateCourse(newCourse, id, teacher);
                } else {
                        throw new ResourcePersistenceException("Sorry, but that could not be persisted!");
                }
        }

        public void deleteCourse(String id) {
                if (isClassIDValid(id)) {
                        schoolRepo.deleteCourse(id);
                }
        }

        public void deregister(String id) {
                if (isClassIDValid(id)) {
                        schoolRepo.deleteEnrolled(id, this.authStudent.getUsername());
                }
        }


        public boolean isCourseValid(Course course) {
                try {
                        if (course == null) return false;
                        if (course.getName() == null || course.getName().trim().equals(""))
                                throw new InvalidRequestException("Course name cannot be null");
                        if (course.getClassID() == null || course.getClassID().trim().equals(""))
                                throw new InvalidRequestException("Class ID cannot be blank or null");
                        if (course.getDesc() == null || course.getDesc().trim().equals(""))
                                throw new InvalidRequestException("Course description cannot be blank or null");
                        if (course.getTeacher() == null) throw new InvalidRequestException("Teacher cannot be null");

                        isClassIDValid(course.getClassID());

                        // Verify that the course description is up to par.
                        if (course.getDesc().length() < 20 && course.getDesc().length() > 50)
                                throw new InvalidRequestException("Course description must be at least twenty characters and no more than fifty characters");

                        // Verify that the course name is within valid boundaries.
                        if (course.getName().length() < 5 && course.getName().length() <= 20)
                                throw new InvalidRequestException("Course name must be at least five characters and less than or equal to twenty characters");
                } catch (InvalidRequestException ire) {
                        System.out.println(ire.getMessage());
                        return false;
                }
                return true;
        }

        // This verifies that students are valid and fit to be placed in the system.
        public boolean isUserValid(Student user) {
                if (user == null) return false;
                if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
                if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
                if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
                if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
                this.isUserUnique(user.getUsername());
                this.isEmailUnique(user.getEmail());
                return true;
        }

        public boolean isClassIDValid(String ClassID) {
                // Verify that the class ID is exactly six characters long.
                if (ClassID.length() != 6) throw new RuntimeException("Class ID must be exactly six characters long!");

                // Verify that the class ID is not already taken.
                if (isClassIDTaken(ClassID)) throw new RuntimeException("Class ID cannot already be taken!");

                // Verify that all characters in ClassID are uppercase
                for (int i=0; i<ClassID.length(); i++) {
                        if (Character.isLowerCase(ClassID.charAt(i))) {
                                throw new InvalidRequestException("Class ID must be all capital letters!");
                        }
                }

                return true;
        }

        private boolean isClassIDTaken(String ClassID) {
                return schoolRepo.findCourseByID(ClassID) != null;
        }

        public boolean isUserUnique(String username) {
                return schoolRepo.findStudentByUsername(username) == null;
        }

        public boolean isEmailUnique(String email) {
                return schoolRepo.findStudentByEmail(email) == null;
        }
}
