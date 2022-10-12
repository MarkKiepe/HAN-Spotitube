package nl.han.dea.markkiepe.spotitube.resources.dto;

import nl.han.dea.markkiepe.spotitube.resources.dto.authentication.AuthenticationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationDTOTest {

    @Test
    void creatingDTOUsingConstructorReturnsTheCorrectUsername() {
        // Arrange
        String username = "mark";
        String password = "verySecure";
        AuthenticationDTO account = new AuthenticationDTO(username, password);

        // Act
        var result = account.getUser();

        // Assert
        assertEquals(username, result);
    }

    @Test
    void creatingDTOUsingConstructorReturnsTheCorrectPassword() {
        // Arrange
        String username = "mark";
        String password = "verySecure";
        AuthenticationDTO account = new AuthenticationDTO(username, password);

        // Act
        var result = account.getPassword();

        // Assert
        assertEquals(password, result);
    }

    @Test
    void creatingDTOUsingConstructorAndChangingTheValuesStillReturnsTheCorrectCredentials() {
        // Arrange
        String username = "mark";
        String usernameAfter = "notMark";
        String password = "verySecure";
        String passwordAfter = "notSecure";
        AuthenticationDTO account = new AuthenticationDTO(username, password);

        // Act
        account.setUser(usernameAfter);
        account.setPassword(passwordAfter);
        var usernameResult = account.getUser();
        var passwordResult = account.getPassword();

        // Assert
        assertEquals(usernameAfter, usernameResult);
        assertEquals(passwordAfter, passwordResult);
    }

    @Test
    void creatingDTOUsingEmptyConstructorAndChangingTheValuesStillReturnsTheCorrectCredentials() {
        // Arrange
        String username = "mark";
        String password = "verySecure";
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUser(username);
        authenticationDTO.setPassword(password);

        // Act
        var usernameResult = authenticationDTO.getUser();
        var passwordResult = authenticationDTO.getPassword();

        // Assert
        assertEquals(username, usernameResult);
        assertEquals(password, passwordResult);
    }
}