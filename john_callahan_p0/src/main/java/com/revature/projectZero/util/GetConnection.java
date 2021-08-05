package com.revature.projectZero.util;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.revature.projectZero.exceptions.ResourcePersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.revature.projectZero.util.AppState.closeApp;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Properties;

public class GetConnection {

    private static GetConnection connection = null;
    private final String ipAddress;
    private final String username;
    private final String dbName;
    private final String password;
    private final int port;
    private MongoClient mongoClient;

    private GetConnection() throws Exception {

        Properties appProperties = new Properties();

        try {
            appProperties.load(new FileReader("src/main/resources/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourcePersistenceException("Unable to load properties file.");
        }
        ipAddress = appProperties.getProperty("ipAddress");
        port = Integer.parseInt(appProperties.getProperty("port"));
        dbName = appProperties.getProperty("dbName");
        username = appProperties.getProperty("username");
        password = appProperties.getProperty("password");

        try {
            this.mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(ipAddress, port))))
                            .credential(MongoCredential.createScramSha1Credential(username, dbName, password.toCharArray()))
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Singleton Design; More than one 'GetConnection' cannot exist at any given time.
    public static GetConnection generate() {

        if (connection == null) {
            try {
                connection = new GetConnection();
            } catch(Exception e) {
                Logger logger = LogManager.getLogger(GetConnection.class);
                logger.error(e.getMessage());
                System.out.println("Sorry! We could not find your properties file.");
                closeApp();
            }
        }

        return connection;
    }

    // This will return the actual "uri" information which allows access to the database.
    public MongoClient getConnection() {
        return mongoClient;
    }
}
