package com.revature.projectZero.util;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.revature.projectZero.util.exceptions.ResourcePersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.revature.projectZero.util.AppState.closeApp;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Properties;

/**
    This is a Singleton Factory that pumps out connection data for the Mongo Database.
    Its goal is to reduce the amount of time a developer needs to repeat themselves a million-fold.
    Simply call the 'generate()' method to have an instance of the class,
    then request a uri through 'getConnection'.
 */

public class GetConnection {

    // Initialization of variables
    private static GetConnection connection = null;
    private final String ipAddress;
    private final int port;
    private MongoClient mongoClient;

    // Static implementation of Logger to work with the getConnection() method.
    static Logger logger = LogManager.getLogger(GetConnection.class);

    private GetConnection() throws Exception {

        // Find the '.properties' file and read the data from it.
        Properties appProperties = new Properties();

        try {
            appProperties.load(new FileReader("john_callahan_p0/src/main/resources/applicationProperties.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourcePersistenceException("Unable to load the properties file.");
        }
        ipAddress = appProperties.getProperty("ipAddress");
        port = Integer.parseInt(appProperties.getProperty("port"));
        String dbName = appProperties.getProperty("dbName");
        String username = appProperties.getProperty("username");
        String password = appProperties.getProperty("password");

        // Compile all of the data from the properties file into a MongoClient called, for simplicity, 'mongoClient'.
        try {
            this.mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(ipAddress, port))))
                            .credential(MongoCredential.createScramSha1Credential(username, dbName, password.toCharArray()))
                            .build());
        } catch (Exception e) { // TODO: Fix this asList warning, it is kind of annoying.
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    // Singleton Design; More than one 'GetConnection' cannot exist at any given time.
    public static GetConnection generate() {

        if (connection == null) {
            try {
                connection = new GetConnection();
            } catch(Exception e) {
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