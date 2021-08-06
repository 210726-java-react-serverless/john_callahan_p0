package com.revature.projectZero.pojos;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.revature.projectZero.util.GetConnection;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

public class MongoPOJOTesting {

    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        GetConnection connection = GetConnection.generate();
        MongoClient mongoClient = connection.getConnection();

        try {

            MongoDatabase database = mongoClient.getDatabase("Project0School").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Student> collection = database.getCollection("StudentCredentials", Student.class);
            Student student = collection.find(eq("username", "Test1")).first();
            System.out.println(student);

            Student newStudent = new Student("troubleShooter", "P455w0rd",
                    "Test", "Testerson", "Test@text.com");
            // insert the instance
            collection.insertOne(newStudent);
            // return all documents in the collection
            List<Student> studentData = new ArrayList<>();
            collection.find().into(studentData);
            System.out.println(studentData);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
