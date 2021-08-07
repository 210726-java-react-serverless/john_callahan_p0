package com.revature.projectZero.repositories;

import static com.mongodb.client.model.Filters.eq;
import static com.revature.projectZero.util.GetMongoClient.generate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.util.GetMongoClient;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTestingRepo {

    public static void main( String[] args ) {
        final GetMongoClient connection = generate();
        final MongoClient mongoClient = connection.getConnection();

        try {
            MongoDatabase database = mongoClient.getDatabase("Project0School");
            MongoCollection<Document> collection = database.getCollection("StudentCredentials");
            Document doc = collection.find(eq("username", "Test1")).first();
            if (doc != null) {
                String json = doc.toJson();
                System.out.println(json);

                ObjectMapper mapper = new ObjectMapper();
                Student student = mapper.readValue(json, Student.class);
                student.setStudentID(doc.get("_id").toString());
                System.out.println(student);
            } else {
                System.out.println("Nothing was found related to your search terms");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
