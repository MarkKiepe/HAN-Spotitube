package nl.han.dea.markkiepe.spotitube.resources;

import nl.han.dea.markkiepe.spotitube.services.authentication.AuthenticationService;
import nl.han.dea.markkiepe.spotitube.resources.dto.authentication.AuthenticationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AuthenticationResourceTest {

    private AuthenticationResource sut;
    private AuthenticationService mockedAuthenticationService;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new AuthenticationResource();
        mockedAuthenticationService = mock(AuthenticationService.class);
        sut.setAuthenticationService(mockedAuthenticationService);
    }

    @Test
    void loginRequestCallsTheLoginRequestOnTheItemService() {
        // Arrange
        AuthenticationDTO mockedAuthenticationDTO = mock(AuthenticationDTO.class);

        // Act
        sut.login(mockedAuthenticationDTO);

        // Assert
        verify(mockedAuthenticationService, times(1)).loginRequest(mockedAuthenticationDTO);
    }

}