package com.revature.projectZero.repositories;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTestingRepo {

    public static void main( String[] args ) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb://java-app:revature@100.26.154.55:27017/Project0School";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Project0School");
            MongoCollection<Document> collection = database.getCollection("StudentCredentials");
            Document doc = collection.find(eq("username", "Test1")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("Nothing was found related to your search terms");
            }
        }
    }
}
