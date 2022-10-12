package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.dao.credentials.CredentialsDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.credentials.AccountDoesNotExistException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.datasource.util.DatabaseProperties;
import nl.han.dea.markkiepe.spotitube.datasource.util.SqlProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CredentialsMapperTest {

    private CredentialsMapper sut;

    private static final String EXISTING_USERNAME = "mark";
    private static final String NOT_EXISTING_USERNAME = "ALKFJKELDJFKQELKFJLD";
    private static final String EMPTY_STRING = "";
    private static final String GET_PASSWORD_SCRIPT_NAME = "getAccountHashedPassword";

    @BeforeEach
    void setup() {
        // Arrange
        sut = new CredentialsMapper();
        sut.setDatabaseProperties(new DatabaseProperties());
        sut.setSqlProperties(new SqlProperties());
    }

    @Test
    void gettingCredentialsDaoWhenExistingAccountIsRequested() {
        // Act
        var result = sut.getUserInfo(EXISTING_USERNAME);

        // Assert
        assertNotEquals(null, result);
        assertEquals(CredentialsDao.class, result.getClass());
    }

    @Test
    void whenAnNotExistingAccountIsRequestedAnAccountDoesNotExistExceptionIsThrown() {
        // Act
        var thrown = assertThrows(
                AccountDoesNotExistException.class,
                () -> sut.getUserInfo(NOT_EXISTING_USERNAME)
        );

        // Assert
        assertNotEquals(null, thrown);
        assertEquals(AccountDoesNotExistException.class, thrown.getClass());
    }

    @Test
    void whenAnEmptyStringIsGivenAnAccountDoesNotExistExceptionIsThrown() {
        // Act
        var thrown = assertThrows(
                AccountDoesNotExistException.class,
                () -> sut.getUserInfo(EMPTY_STRING)
        );

        // Assert
        assertNotEquals(null, thrown);
        assertEquals(AccountDoesNotExistException.class, thrown.getClass());
    }

    @Test
    void whenTheSqlScriptIsMissingADatabaseErrorExceptionIsThrown() {
        // Arrange
        SqlProperties properties = mock(SqlProperties.class);
        sut.setSqlProperties(properties);

        // Act
        var thrown = assertThrows(
                DatabaseErrorException.class,
                () -> sut.getUserInfo(EXISTING_USERNAME)
        );

        // Assert
        assertNotEquals(null, thrown);
        assertEquals(DatabaseErrorException.class, thrown.getClass());
    }

}