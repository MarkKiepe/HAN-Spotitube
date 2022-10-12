package nl.han.dea.markkiepe.spotitube.services.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatedUserTest {

    private static final int USERID = 1;
    private static final String USERNAME = "mark";
    private static final String TOKEN = "1234-1234-1234-1234";

    private AuthenticatedUser sut;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new AuthenticatedUser(USERID, USERNAME, TOKEN);
    }

    @Test
    void whenNewUserIsAuthenticatedUserIdIsSetCorrectly() {
        // Act
        var result = sut.getUserId();

        // Assert
        assertEquals(USERID, result);
    }

    @Test
    void whenNewUserIsAuthenticatedTokenIsSetCorrectly() {
        // Act
        var result = sut.getToken();

        // Assert
        assertEquals(TOKEN, result);
    }

    @Test
    void whenNewUserIsAuthenticatedUsernameIsSetCorrectly() {
        // Act
        var result = sut.getUser();

        // Assert
        assertEquals(USERNAME, result);
    }
}