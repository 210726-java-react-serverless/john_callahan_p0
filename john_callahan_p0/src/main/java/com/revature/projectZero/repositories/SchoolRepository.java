package com.revature.projectZero.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.projectZero.pojos.Course;
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

import java.util.ArrayList;
import java.util.List;

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

    public void register(Course course) throws Exception {
        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Course> collection = database.getCollection("enrolled", Course.class);

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
            MongoDatabase p0school = mongoClient.getDatabase("Project0School");
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
    public List<Course> findCourseByOpen(boolean isOpen) {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = p0school.getCollection("classes");
        Document queryDoc = new Document("isOpen", isOpen);
        List<Document> bsonCourses = new ArrayList<>();
        usersCollection.find(queryDoc).into(bsonCourses);

        List<Course> courses = new ArrayList<>();
        try {

            // Iterate over bsonCourses and convert them all to json
            for (int i = 0; i < bsonCourses.size(); i++) {
                String json = bsonCourses.get(i).toJson();
                Course currentCourse = mapper.readValue(json, Course.class);
                courses.add(i, currentCourse);
            }
        } catch (JsonProcessingException jse) {
            logger.error("Threw a JsonProcessingException at SchoolRepository::isUsernameTaken, full StackTrace follows: " + jse);
        }
        return courses;
    }


    // This is used so that students can see their courses.
    public List<Course> findCourseByUsername(String username) {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = p0school.getCollection("enrolled");
        Document queryDoc = new Document("student", username);
        List<Document> bsonCourses = new ArrayList<>();
        usersCollection.find(queryDoc).into(bsonCourses);

        List<Course> courses = new ArrayList<>();
        try {

            // Iterate over bsonCourses and convert them all to json
            for (int i = 0; i<bsonCourses.size(); i++) {
                String json = bsonCourses.get(i).toJson();
                Course currentCourse = mapper.readValue(json, Course.class);
                courses.add(i, currentCourse);
            }
        } catch( JsonProcessingException jse) {
            logger.error(jse.getMessage());
            logger.error("Threw a JsonProcessingException at SchoolRepository::isUsernameTaken, full StackTrace follows: " + jse);
        }
        return courses;

    }

    // This method is primarily used by Teachers to find classes that they put onto the database, for deletion and updates.
    public List<Course> findCourseByTeacher(String lastName) {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = p0school.getCollection("classes");
        Document queryDoc = new Document("teacher", lastName);
        List<Document> bsonCourses = new ArrayList<>();
        usersCollection.find(queryDoc).into(bsonCourses);

        List<Course> courses = new ArrayList<>();
        try {

            // Iterate over bsonCourses and convert them all to json
            for (int i = 0; i<bsonCourses.size(); i++) {
                String json = bsonCourses.get(i).toJson();
                Course currentCourse = mapper.readValue(json, Course.class);
                courses.add(i, currentCourse);
            }
        } catch( JsonProcessingException jse) {
        logger.error(jse.getMessage());
        logger.error("Threw a JsonProcessingException at SchoolRepository::isUsernameTaken, full StackTrace follows: " + jse);
    }
        return courses;

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
    // TODO: Write the course update method!

    // ====DELETE====
    // TODO: Write the Teacher course deletion method!

    // TODO: Write the Student enrolled course deletion method!
}
