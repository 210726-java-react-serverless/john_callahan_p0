package com.revature.projectZero.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.util.GetConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.PrintStream;

import static com.revature.projectZero.util.GetConnection.generate;

/**
    Create, Read, Update, and Delete using this script. It can also perform certain queries to get data
    from the database.
 */

public class SchoolRepository implements CrudRepository {

    Logger logger = LogManager.getLogger(SchoolRepository.class);
    private final GetConnection connection = generate();
    private final MongoClient mongoClient = connection.getConnection();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object findByID(int id) {
        return null;
    }

    @Override
    public Object save(Object newResource) {
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
    public Student findStudentByCredentials(String username, String password) {

        try {

            MongoDatabase bookstoreDatabase = mongoClient.getDatabase("bookstore");
            MongoCollection<Document> usersCollection = bookstoreDatabase.getCollection("users");
            Document queryDoc = new Document("username", username).append("password", password);
            Document authUserDoc = usersCollection.find(queryDoc).first();

            if (authUserDoc == null) {
                return null;
            }

            Student student = mapper.readValue(authUserDoc.toJson(), Student.class);
            student.setStudentID(authUserDoc.get("_id").toString());
            System.out.println(student);

            return student;

        } catch(Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Student findStudentByUsername(String username) {
        return null;
    }

    @Override
    public Faculty findFacultyByCredentials(String username, String password) {
        return null;
    }

    @Override
    public Faculty findFacultyByUsername(String username) {
        return null;
    }
}
