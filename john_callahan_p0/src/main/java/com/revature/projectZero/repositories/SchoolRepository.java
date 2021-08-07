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
    Create, Read, Update, and Delete using this script. It can also perform certain queries to get data
    from the database.
 */

public class SchoolRepository implements CrudRepository {

    // These are used quite often in the CRUD methods.
    Logger logger = LogManager.getLogger(SchoolRepository.class);
    private final GetMongoClient connection = generate();
    private final MongoClient mongoClient = connection.getConnection();
    private final ObjectMapper mapper = new ObjectMapper();
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    Student student;


    @Override
    public Object findByID(int id) {
        return null;
    }

    @Override
    public Object save(Object o) {
        return null;
    }


    // This method persists users to the database.
    public Student save(Student newStudent) {

        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Student> collection = database.getCollection("StudentCredentials", Student.class);

            // this inserts the instance into the "StudentCredentials" database.
            collection.insertOne(newStudent);

        } catch(Exception e) {
            logger.error(e.getMessage());
            logger.error("Threw an exception at SchoolRepository::save(), full StackTrace follows: " + e);
        }
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
    public Student findStudentByCredentials(String username, int hashPass) {

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
            logger.error(e.getMessage());
            logger.error("Threw an exception at SchoolRepository::findStudentByCredentials(), full StackTrace follows: " + e);
        }

        return null;
    }

    @Override
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
            logger.error(jse.getMessage());
            logger.error("Threw a JsonProcessingException at SchoolRepository::isUsernameTaken, full StackTrace follows: " + jse);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByUsername(String username) { // TODO: Implement findCourseByUsername!
        return null;
    }

    @Override
    public List<Course> findCourseByTeacher(String lastName) {
        MongoDatabase p0school = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = p0school.getCollection("classes");
        Document queryDoc = new Document("teacher", lastName);
        List<Document> bsonCourses = new ArrayList<>();
        usersCollection.find(queryDoc).into(bsonCourses);

        List<Course> courses = new ArrayList<>();
        try {

            // Iterate over bsonCourses and convert them all to json
            for (int i =0; i<bsonCourses.size(); i++) {
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

    @Override
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

    @Override
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
            logger.error(e.getMessage());
            logger.error("Threw an exception at SchoolRepository::findStudentByCredentials(), full StackTrace follows: " + e);
        }

        return null;
    }
}
