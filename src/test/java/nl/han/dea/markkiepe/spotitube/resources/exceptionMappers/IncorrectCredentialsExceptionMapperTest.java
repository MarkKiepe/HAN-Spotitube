package nl.han.dea.markkiepe.spotitube.resources.exceptionMappers;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class IncorrectCredentialsExceptionMapperTest {

    private IncorrectCredentialsExceptionMapper sut;
    private IncorrectCredentialsException exception;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new IncorrectCredentialsExceptionMapper();
        exception = mock(IncorrectCredentialsException.class);
    }

    @Test
    void whenAnIncorrectCredentialsExceptionIsThrownAndTheResponseIsRequestedThisIsReturned() {
        // Act
        Response result = sut.toResponse(exception);

        // Assert
        assertEquals(Response.Status.CONFLICT, result.getStatusInfo());
    }

}