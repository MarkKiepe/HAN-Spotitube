package nl.han.dea.markkiepe.spotitube.datasource.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DatabasePropertiesTest {

    private DatabaseProperties databaseProperties;

    @BeforeEach
    void setup() {
        // Arrange
        databaseProperties = new DatabaseProperties();
    }

    @Test
    void gettingConnectionStringFromDatabaseReturnsAStringAndNotNull() {
        // Act
        var result = databaseProperties.getConnectionString();

        // Assert
        assertNotEquals(null, result);
        assertEquals(((Object) "string" ).getClass().getName(), ((Object) result).getClass().getName() );
    }

    @Test
    void gettingPropertiesFileReturnsAPropertyFile() {
        // Act
        var result = databaseProperties.getPropertyFile();

        // Assert
        assertEquals(Properties.class, result.getClass());
    }
}