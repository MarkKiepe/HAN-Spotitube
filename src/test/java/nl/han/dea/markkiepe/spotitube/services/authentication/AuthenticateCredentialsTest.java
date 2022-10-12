package nl.han.dea.markkiepe.spotitube.services.authentication;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.datasource.CredentialsMapper;
import nl.han.dea.markkiepe.spotitube.datasource.dao.credentials.CredentialsDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.credentials.AccountDoesNotExistException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticateCredentialsTest {

    private AuthenticateCredentials sut;
    private CredentialsMapper mockedCredentialsMapper;

    private static final String ACCEPTED_USERNAME = "mark";
    private static final String ACCEPTED_PASSWORD = "verysecure";
    private static final String ACCEPTED_HASHED_PASSWORD = "f616ac1c494f605ff1fa305b263d055e4fdc2446e8efb9637b0a1906bdc3514f1e7ef71ed83e790a10d82d0d79b6abad95566b7834ab2d7949f92fa90dc2e4e2";
    private static final String NOT_ACCEPTED_USERNAME = "ab";
    private static final String NOT_ACCEPTED_PASSWORD = "hi";
    private static final String EMPTY_STRING = "";

    @BeforeEach
    void setup() {
        // Arrange
        sut = new AuthenticateCredentials();
        mockedCredentialsMapper = mock(CredentialsMapper.class);
        sut.setCredentialsMapper(mockedCredentialsMapper);
    }

    @Test
    void whenCorrectAccountCredentialsAreGivenACorrectUserIdIsGivenBack() {
        // Arrange
        int userId = 123;
        //
        CredentialsDao mockedCredentialsDao = mock(CredentialsDao.class);
        when(mockedCredentialsDao.getUserId()).thenReturn(userId);
        when(mockedCredentialsDao.getPassword()).thenReturn(ACCEPTED_HASHED_PASSWORD);
        //
        when(mockedCredentialsMapper.getUserInfo(ACCEPTED_USERNAME)).thenReturn(mockedCredentialsDao);

        // Act
        var result = sut.attemptLogin(ACCEPTED_USERNAME, ACCEPTED_PASSWORD);

        // Assert
        verify(mockedCredentialsMapper).getUserInfo(ACCEPTED_USERNAME);
        assertEquals(userId, result);
    }

    @Test
    void whenAnAccountExistButTheEnteredPasswordIsWrongAnIncorrectCredentialsExceptionIsThrown() {
        // Arrange
        int userId = 123;
        //
        CredentialsDao mockedCredentialsDao = mock(CredentialsDao.class);
        when(mockedCredentialsDao.getUserId()).thenReturn(userId);
        when(mockedCredentialsDao.getPassword()).thenReturn(ACCEPTED_HASHED_PASSWORD);
        //
        when(mockedCredentialsMapper.getUserInfo(ACCEPTED_USERNAME)).thenReturn(mockedCredentialsDao);

        // Act
        var result = assertThrows(
                IncorrectCredentialsException.class,
                () -> sut.attemptLogin(ACCEPTED_USERNAME, "wrongpassword")
        );

        // Assert
        verify(mockedCredentialsMapper, times(1)).getUserInfo(ACCEPTED_USERNAME);
        assertEquals(IncorrectCredentialsException.class, result.getClass());
    }

    @Test
    void whenIncorrectAccountCredentialsAreGivenAnIncorrectCredentialsExceptionIsThrown() {
        // Act
        var result = assertThrows(
                IncorrectCredentialsException.class,
                () -> sut.attemptLogin(NOT_ACCEPTED_USERNAME, NOT_ACCEPTED_PASSWORD)
        );

        // Assert
        verify(mockedCredentialsMapper, times(0)).getUserInfo(NOT_ACCEPTED_USERNAME);
        assertEquals(IncorrectCredentialsException.class, result.getClass());
    }

    @Test
    void whenEmptyAccountCredentialsAreGivenAnIncorrectCredentialsExceptionIsThrown() {
        // Act
        var result = assertThrows(
                IncorrectCredentialsException.class,
                () -> sut.attemptLogin(EMPTY_STRING, EMPTY_STRING)
        );

        // Assert
        verify(mockedCredentialsMapper, times(0)).getUserInfo(EMPTY_STRING);
        assertEquals(IncorrectCredentialsException.class, result.getClass());
    }

    @Test
    void whenAnAccountDoesNotExistExceptionIsThrownTheIncorrectCredentialsExceptionIsThrown() {
        // Arrange
        when(mockedCredentialsMapper.getUserInfo(ACCEPTED_USERNAME)).thenThrow(AccountDoesNotExistException.class);

        // Act
        var result = assertThrows(
                IncorrectCredentialsException.class,
                () -> sut.attemptLogin(ACCEPTED_USERNAME, ACCEPTED_PASSWORD)
        );

        // Assert
        verify(mockedCredentialsMapper, times(1)).getUserInfo(ACCEPTED_USERNAME);
        assertEquals(IncorrectCredentialsException.class, result.getClass());
    }

    @Test
    void whenAnDatabaseErrorExceptionIsThrownTheIncorrectCredentialsExceptionIsThrown() {
        // Arrange
        when(mockedCredentialsMapper.getUserInfo(ACCEPTED_USERNAME)).thenThrow(DatabaseErrorException.class);

        // Act
        var result = assertThrows(
                IncorrectCredentialsException.class,
                () -> sut.attemptLogin(ACCEPTED_USERNAME, ACCEPTED_PASSWORD)
        );

        // Assert
        verify(mockedCredentialsMapper, times(1)).getUserInfo(ACCEPTED_USERNAME);
        assertEquals(IncorrectCredentialsException.class, result.getClass());
    }
}