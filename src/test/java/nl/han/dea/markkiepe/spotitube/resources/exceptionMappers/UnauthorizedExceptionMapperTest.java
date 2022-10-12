package nl.han.dea.markkiepe.spotitube.resources.exceptionMappers;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UnauthorizedExceptionMapperTest {

    private UnauthorizedExceptionMapper sut;
    private UnauthorizedException exception;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new UnauthorizedExceptionMapper();
        exception = mock(UnauthorizedException.class);
    }

    @Test
    void whenAnUnauthorizedExceptionIsThrownAndTheResponseIsRequestedThisIsReturned() {
        // Act
        Response result = sut.toResponse(exception);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED, result.getStatusInfo());
    }

}