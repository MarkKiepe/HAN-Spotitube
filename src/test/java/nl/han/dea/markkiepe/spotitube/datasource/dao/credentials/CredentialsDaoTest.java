package nl.han.dea.markkiepe.spotitube.datasource.dao.credentials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CredentialsDaoTest {

    private static final int USER_ID = 1;
    private static final String PASSWORD = "vErYsEcUrE";

    @Test
    void creatingDataAccessObjectFromConstructorReturnsTheRightUserId() {
        // Arrange
        CredentialsDao sut = new CredentialsDao(USER_ID, PASSWORD);

        // Act
        var result = sut.getUserId();

        // Assert
        assertEquals(USER_ID, result);
    }

    @Test
    void creatingDataAccessObjectFromConstructorReturnsTheRightPassword() {
        // Arrange
        CredentialsDao sut = new CredentialsDao(USER_ID, PASSWORD);

        // Act
        var result = sut.getPassword();

        // Assert
        assertEquals(PASSWORD, result);
    }
}