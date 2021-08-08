package com.revature.projectZero.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.pojos.Enrolled;
import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.util.GetMongoClient;
import com.revature.projectZero.util.exceptions.InvalidRequestException;
import com.revature.projectZero.util.exceptions.ResourcePersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.revature.projectZero.util.GetMongoClient.generate;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
    Create, Read, Update, and Delete using this script. It also performs certain queries to get data
    from the database, extensions of the feat of reading. This is the closest thing I have to a "god class"
    inside of my application.
 */

public class SchoolRepository {

    // These are used quite often in the CRUD methods.
    Logger logger = LogManager.getLogger(SchoolRepository.class);
    private final GetMongoClient connection = generate();
    private final MongoClient mongoClient = connection.getConnection();
    private final ObjectMapper mapper = new ObjectMapper();
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    Student student;

    // ====CREATE====
    // This method persists users to the database.
    public void save(Student newStudent) throws Exception {

        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Student> collection = database.getCollection("StudentCredentials", Student.class);

            // this inserts the instance into the "StudentCredentials" database.
            collection.insertOne(newStudent);

        } catch(Exception e) {
            logger.error("Threw an exception at SchoolRepository::save(), full StackTrace follows: " + e);
            throw new ResourcePersistenceException("That user could not be placed in the database!");
        }
    }

    public void enroll(Enrolled course) throws Exception {
        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Enrolled> collection = database.getCollection("enrolled", Enrolled.class);

            // this inserts the instance into the "StudentCredentials" database.
            collection.insertOne(course);

        } catch(Exception e) {
            logger.error("Threw an exception at SchoolRepository::register(), full StackTrace follows: " + e);
            throw new ResourcePersistenceException("We could not enroll you in that course!");
        }
    }

    public void newCourse(Course course) throws Exception {
        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Course> collection = database.getCollection("classes", Course.class);

            // this inserts the instance into the "StudentCredentials" database.
            collection.insertOne(course);

        } catch(Exception e) {
            logger.error("Threw an exception at SchoolRepository::newCourse(), full StackTrace follows: " + e);
            throw new ResourcePersistenceException("We're sorry, but we could not register your class!");
        }
    }


    // ====READ====
    // This is used for the login function in the service layer.
    public Student findStudentByCredentials(String username, int hashPass) throws Exception {

        try {
            MongoDatabase p0school = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usersCollection = p0school.getCollection("StudentCredentials");
            Document queryDoc = new Document("username", username).append("hashPass", hashPass);
            Document userCredentials = usersCollection.find(queryDoc).first();

            if (userCredentials == null) {
                return null;
            }

            Student student = mapper.readValue(userCredentials.toJson(), Student.class);
            student.setStudentID(userCredentials.get("_id").toString());

            return student;

        } catch(Exception e) {
            logger.error("Threw an exception at SchoolRepository::findStudentByCredentials(), full StackTrace follows: " + e);
            throw new InvalidRequestException("An error has occurred while processing your request!");
        }
    }

    // This method is primarily for students to find classes that are accepting new entries.
    public List<Course> findCourseByOpen() {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Course> usersCollection = p0school.getCollection("classes", Course.class);
        Document queryDoc = new Document("isOpen", true);
        List<Course> courses = new ArrayList<>();
        usersCollection.find(queryDoc).into(courses);

        return courses;
    }

    public Course findCourseByID(String id) {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Course> usersCollection = p0school.getCollection("classes", Course.class);
        Document queryDoc = new Document("classID", id);
        return usersCollection.find(queryDoc).first();
    }


    // This is used so that students can see their courses.
    public List<Enrolled> findEnrolledByUsername(String username) {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Enrolled> usersCollection = p0school.getCollection("enrolled", Enrolled.class);
        Document queryDoc = new Document("student", username);
        List<Enrolled> courses = new ArrayList<>();
        usersCollection.find(queryDoc).into(courses);

        return courses;
    }

    // This method is primarily used by Teachers to find classes that they put onto the database, for deletion and updates.
    public List<Course> findCourseByTeacher(String lastName) {

        try {
            MongoDatabase p0school = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Course> usersCollection = p0school.getCollection("classes", Course.class);
            Document queryDoc = new Document("teacher", lastName);
            List<Course> courses = new ArrayList<>();

            usersCollection.find(queryDoc).into(courses);

            return courses;
        } catch (Exception e) {
            System.out.println("An exception has occurred!" + e.getMessage());
            logger.error("Problem occurred when parsing the data from Mongo. Full Stack Trace follows:: " + e);
        }
        return null;
    }

    // Primarily used to ensure that a Student's input username is unique.
    public Student findStudentByUsername(String username)  {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = p0school.getCollection("StudentCredentials");
        Document queryDoc = new Document("username", username);
        Document isUsernameTaken = usersCollection.find(queryDoc).first();


        try {
            if (isUsernameTaken != null) {
                String json = isUsernameTaken.toJson();
                Student student = mapper.readValue(json, Student.class);
                student.setStudentID(isUsernameTaken.get("_id").toString());
                this.student = student;

            } else {
                return null;
            }

        } catch( JsonProcessingException jse) {
            logger.error(jse.getMessage());
            logger.error("Threw a JsonProcessingException at SchoolRepository::isUsernameTaken, full StackTrace follows: " + jse);
        }

        return student;
    }

    // This checks to verify if a Student input a unique Email from all others in the system.
    public Student findStudentByEmail(String email)  {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = p0school.getCollection("StudentCredentials");
        Document queryDoc = new Document("email", email);
        Document isEmailTaken = usersCollection.find(queryDoc).first();


        try {
            if (isEmailTaken != null) {
                String json = isEmailTaken.toJson();
                Student student = mapper.readValue(json, Student.class);
                student.setStudentID(isEmailTaken.get("_id").toString());
                this.student = student;

            } else {
                return null;
            }

        } catch( JsonProcessingException jse) {
            logger.error(jse.getMessage());
            logger.error("Threw a JsonProcessingException at SchoolRepository::isEmailTaken, full StackTrace follows: " + jse);
        }

        return student;
    }

    // This is used in the login function to determine if the user is valid.
    public Faculty findFacultyByCredentials(String username, int hashPass){

        try {
            MongoDatabase p0school = mongoClient.getDatabase("Project0School");
            MongoCollection<Document> usersCollection = p0school.getCollection("FacultyCredentials");
            Document queryDoc = new Document("username", username).append("hashPass", hashPass);
            Document userCredentials = usersCollection.find(queryDoc).first();

            if (userCredentials == null) {
                return null;
            }

            Faculty authFac = mapper.readValue(userCredentials.toJson(), Faculty.class);
            authFac.setTeacherID(userCredentials.get("_id").toString());

            return authFac;

        } catch(Exception e) {
            logger.error("Threw an exception at SchoolRepository::findStudentByCredentials(), full StackTrace follows: " + e);
        }

        return null;
    }

    // ====UPDATE====
    // This class allows teachers to update the information related to their courses.
    public boolean updateCourse(Course course, String id, String teacher) throws Exception {

        try { // TODO: This does not work! Perhaps a delete, followed by a persist function?
            MongoDatabase p0school = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);;
            MongoCollection<Document> collection = p0school.getCollection("classes");
            MongoCollection<Course> updatedCourse = p0school.getCollection("classes", Course.class);
            Document queryDoc = new Document("ClassID", id).append("teacher", teacher);
            Document oldCourse = collection.find(queryDoc).first();

            if(oldCourse != null) {
                updatedCourse.replaceOne(queryDoc, course);
                return true;
            }

        } catch(Exception e) {
            logger.error("Threw an exception at SchoolRepository::updateCourse(), full StackTrace follows: " + e);
            throw new ResourcePersistenceException("We're sorry, but that could not be updated!");
        }
        return false;
    }

    // ====DELETE====
    // This method is used by teachers to get rid of a course.
    public void deleteCourse(String courseID) throws Exception{
        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School");
            MongoCollection<Document> collection = database.getCollection("classes");
            MongoCollection<Document> enrolledCollection = database.getCollection("enrolled");
            Document queryDoc = new Document("classID", courseID);
            Document deletedCourse = collection.find(queryDoc).first();
            List<Document> enrolledCourses = new ArrayList<>();
            enrolledCollection.find(queryDoc).into(enrolledCourses);

            // this deletes all instances of the course which students might be enrolled to.
            if(deletedCourse != null) {
                for (Document enrolledCours : enrolledCourses) {
                    enrolledCollection.deleteMany(enrolledCours);
                }
            }

            // this deletes the instance from the "classes" database.
            if(deletedCourse != null) {
                collection.deleteOne(deletedCourse);
            }

        } catch (Exception e) {
            logger.error("Threw an exception at SchoolRepository::deleteCourse(), full StackTrace follows: " + e);
            throw new InvalidRequestException("That user could not be removed from the database!");
        }
    }

    public void deleteEnrolled(String courseID, String username) throws Exception{
        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Enrolled> collection = database.getCollection("enrolled", Enrolled.class);
            Document queryDoc = new Document("id", courseID).append("username", username);

            // this deletes the instance from the "classes" database.
            if(collection.find(queryDoc).first() != null) {
                collection.deleteOne((Bson) Objects.requireNonNull(collection.find(queryDoc).first()));
            }

        } catch (Exception e) {
            logger.error("Threw an exception at SchoolRepository::deleteCourse(), full StackTrace follows: " + e);
            throw new InvalidRequestException("That user could not be removed from the database!");
        }
    }
}