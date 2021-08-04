package com.revature.projectZero.pojos;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoPOJOTesting {

    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb://java-app:revature@100.26.154.55:27017/StudentCredentials";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("StudentCredentials").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Student> collection = database.getCollection("StudentCredentials", Student.class);
            Student student = collection.find(eq("username", "Test User")).first();
            System.out.println(student);

            Student newStudent = new Student("troubleShooter", "P455w0rd",
                    "Test", "Testerson", "Test@text.com");
            // insert the instance
            collection.insertOne(newStudent);
            // return all documents in the collection
            List<Student> studentData = new ArrayList<>();
            collection.find().into(studentData);
            System.out.println(studentData);
        }
    }
}
