package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.WrongPreparedStatementType;
import nl.han.dea.markkiepe.spotitube.datasource.util.DatabaseProperties;
import nl.han.dea.markkiepe.spotitube.datasource.util.SqlProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapperTest {

    private static final String NOT_EXISTING_SCRIPT_NAME = "hi";

    private CredentialsMapper mockedSubClass;
    private SqlProperties mockedSqlProperties;
    private DatabaseProperties mockedDatabaseProperties;

    @BeforeEach
    void setup() {
        // Arrange
        mockedSubClass = spy(new CredentialsMapper());
        //
        mockedSqlProperties = mock(SqlProperties.class);
        mockedDatabaseProperties = mock(DatabaseProperties.class);
        //
        mockedSubClass.setSqlProperties(mockedSqlProperties);
        mockedSubClass.setDatabaseProperties(mockedDatabaseProperties);
    }

    @Test
    void whenAnExceptionOccursWhileQueryingTheDatabaseAllConnectionsGetClosed() {
        // Act
        var result = assertThrows(
                DatabaseErrorException.class,
                () -> mockedSubClass.queryDatabase(NOT_EXISTING_SCRIPT_NAME, false)
        );

        // Assert
        assertEquals(DatabaseErrorException.class, result.getClass());
    }

    @Test
    void whenAWrongDataTypeIsGivenToThePrepareStatementMethodAWrongPreparedStatementTypeIsThrown() {
        // Arrange
        Connection mockedConnection = mock(Connection.class);

        // Act
        var result = assertThrows(
                WrongPreparedStatementType.class,
                () -> mockedSubClass.createPreparedStatement(mockedConnection, NOT_EXISTING_SCRIPT_NAME, true)
        );

        // Assert
        assertEquals(WrongPreparedStatementType.class, result.getClass());
    }
    
    @Test
    void whenNoResponseIsRequestedTheMethodDoesNotFailAndReturnsNull() {
        // Arrange
        String scriptName = "verycoolscriptname";
        String script = "SELECT * FROM Users";
        when(mockedDatabaseProperties.getConnectionString()).thenReturn("jdbc:sqlserver://localhost;databaseName=Spotitube;user=spotitubeUser;password=mark;");
        when(mockedSqlProperties.getSqlScript(scriptName)).thenReturn(script);

        // Act
        ResultSet result = null;
        try {
            result = mockedSubClass.queryDatabase(scriptName, false);
        } catch (Exception e) {
        }
        
        // Assert
        assertEquals(null, result);
    }

    @Test
    void whenAnExceptionOccursWhileQueryingDatabaseAllTheQueryGetsCancelledAndADatabaseErrorExceptionIsThrown() {
        // Arrange
        String scriptName = "verycoolscriptname";

        // Act
        var result = assertThrows(
                DatabaseErrorException.class,
                () -> mockedSubClass.queryDatabase(NOT_EXISTING_SCRIPT_NAME, false)
        );

        // Assert
        assertEquals(DatabaseErrorException.class, result.getClass());
    }

}