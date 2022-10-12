package nl.han.dea.markkiepe.spotitube.datasource.util;

import nl.han.dea.markkiepe.spotitube.datasource.exceptions.SqlScriptDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SqlPropertiesTest {

    private SqlProperties sqlProperties;

    private static final String EXISTING_SCRIPT_NAME = "getAccountHashedPassword";
    private static final String NOT_EXISTING_SCRIPT_NAME = "ALKJFLKEQJFUIEWUIGHFNAQEJ:";
    private static final String EMPTY_SCRIPT_NAME = "";

    @BeforeEach
    void setup() {
        // Arrange
        sqlProperties = new SqlProperties();
    }

    @Test
    void requestingScriptFromSqlPropertiesReturnsAScriptInStringFormat() {
        // Act
        var result = sqlProperties.getSqlScript(EXISTING_SCRIPT_NAME);

        // Assert
        assertNotEquals(null, result);
        assertEquals(((Object) "string" ).getClass().getName(), ((Object) result).getClass().getName() );
    }

    @Test
    void requestingScriptFromSqlPropertiesThatDoesNotExistThrowsAnSqlScriptDoesntExistException() {
       // Act
        var thrown = assertThrows(
                SqlScriptDoesntExistException.class,
                () -> sqlProperties.getSqlScript(NOT_EXISTING_SCRIPT_NAME)
        );

        // Assert
        assertNotEquals(null, thrown);
        assertEquals(SqlScriptDoesntExistException.class, thrown.getClass());
    }

    @Test
    void requestingScriptFromSqlPropertiesWithAnEmptyStringThrowsAnSqlScriptDoesntExistException() {
        // Act
        var thrown = assertThrows(
                SqlScriptDoesntExistException.class,
                () -> sqlProperties.getSqlScript(EMPTY_SCRIPT_NAME)
        );

        // Assert
        assertNotEquals(null, thrown);
        assertEquals(SqlScriptDoesntExistException.class, thrown.getClass());
    }

    @Test
    void requestingPropertiesFileReturnsAPropertiesFile() {
        // Act
        var result = sqlProperties.getPropertyFile();

        // Assert
        assertEquals(Properties.class, result.getClass());
    }


}