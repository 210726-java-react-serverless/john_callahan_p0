package com.revature.projectZero.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.revature.projectZero.util.GetConnection.generate;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
    Create, Read, Update, and Delete using this script. It can also perform certain queries to get data
    from the database.
 */

public class SchoolRepository implements CrudRepository {

    // These are used quite often in the CRUD methods.
    Logger logger = LogManager.getLogger(SchoolRepository.class);
    private final GetConnection connection = generate();
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
            Student student = collection.find(eq("username", "Test1")).first();
            System.out.println(student);

            // this inserts the instance into the "StudentCredentials" database.
            collection.insertOne(newStudent);

        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return newStudent;
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

            MongoDatabase bookstoreDatabase = mongoClient.getDatabase("Project0School");
            MongoCollection<Document> usersCollection = bookstoreDatabase.getCollection("StudentCredentials");
            Document queryDoc = new Document("username", username).append("password", password);
            Document userCredentials = usersCollection.find(queryDoc).first();

            if (userCredentials == null) {
                return null;
            }

            Student student = mapper.readValue(userCredentials.toJson(), Student.class);
            student.setStudentID(userCredentials.get("_id").toString());

            return student;

        } catch(Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Student findStudentByUsername(String username)  {
        MongoDatabase bookstoreDatabase = mongoClient.getDatabase("Project0School");
        MongoCollection<Document> usersCollection = bookstoreDatabase.getCollection("StudentCredentials");
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
            jse.printStackTrace();
            logger.error(jse.getMessage());
        }

        return student;
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
