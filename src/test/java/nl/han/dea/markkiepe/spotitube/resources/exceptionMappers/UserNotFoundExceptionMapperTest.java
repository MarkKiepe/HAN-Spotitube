package nl.han.dea.markkiepe.spotitube.resources.exceptionMappers;

import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserNotFoundExceptionMapperTest {

    private UserNotFoundExceptionMapper sut;
    private UserNotFoundException exception;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new UserNotFoundExceptionMapper();
        exception = mock(UserNotFoundException.class);
    }

    @Test
    void whenAnUserNotFoundExceptionIsThrownAndTheResponseIsRequestedThisIsReturned() {
        // Act
        Response result = sut.toResponse(exception);

        // Assert
        assertEquals(Response.Status.NOT_FOUND, result.getStatusInfo());
    }

}