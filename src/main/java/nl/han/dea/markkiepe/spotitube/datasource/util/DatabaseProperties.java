package nl.han.dea.markkiepe.spotitube.datasource.util;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible for getting the database properties that are used to make
 * a connection with the database.
 *
 * @see PropertyManager
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class DatabaseProperties extends PropertyManager {

    private static final String DB_CONNECTION_STRING = "connectionString";
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";

    /**
     * Constructor that creates the Database Properties class.<br>
     * @since 1.0
     */
    @Inject
    public DatabaseProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass()
                    .getClassLoader()
                    .getResourceAsStream(DATABASE_PROPERTIES_FILENAME)
            );
        } catch(IOException e) {
            e.printStackTrace();
        }
        super.setPropertyFile(properties);
    }

    /**
     * Gets the Connection String that is used to make a connection with the database.
     * @implNote This method can return {@code null} if the properties cannot be found or if the connection string
     * cannot be found inside of the properties.
     * @return {@link String} connection string
     * @since 1.0
     */
    public String getConnectionString(){
        return properties.getProperty(DB_CONNECTION_STRING);
    }

}
