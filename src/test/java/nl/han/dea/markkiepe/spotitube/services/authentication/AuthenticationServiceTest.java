package nl.han.dea.markkiepe.spotitube.services.authentication;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;
import nl.han.dea.markkiepe.spotitube.resources.dto.authentication.AuthenticationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private AuthenticationService sut;
    private TokenGenerator mockedTokenGenerator;
    private AuthenticateCredentials mockedAuthenticateCredentials;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new AuthenticationService();
        mockedTokenGenerator = mock(TokenGenerator.class);
        mockedAuthenticateCredentials = mock(AuthenticateCredentials.class);
        sut.setGenerator(mockedTokenGenerator);
        sut.setAuthenticateCredentials(mockedAuthenticateCredentials);
    }

    @Test
    void loginRequestUsesTheAttemptLogInMethodInAuthenticateCredentials() {
        // Arrange
        AuthenticationDTO authenticationDTO = mock(AuthenticationDTO.class);

        // Act
        try {
            sut.loginRequest(authenticationDTO);
        } catch(Exception e) {
        }

        // Assert
        verify(mockedAuthenticateCredentials, times(1)).attemptLogin(null, null);
    }

    @Test
    void loginRequestWithIncorrectAccountDetailsThrowsIncorrectAccountDetailsException() {
        // Arrange
        AuthenticationDTO authenticationDTO = mock(AuthenticationDTO.class);

        // Act
        var result = assertThrows(
                IncorrectCredentialsException.class,
                () -> sut.loginRequest(authenticationDTO)
        );

        // Assert
        assertEquals(IncorrectCredentialsException.class, result.getClass());
    }

    @Test
    void getUserIdFromTokenUsesGetTokenAndGetUserIdOnMock() {
        // Arrange
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        String token = "abc";
        when(user.getToken()).thenReturn(token);
        ArrayList<AuthenticatedUser> list = new ArrayList<>();
        list.add(user);
        sut.setUsers(list);

        // Act
        var result = sut.getUserIdFromToken(token);

        // Assert
        verify(user, times(1)).getToken();
        verify(user).getUserId();
    }

    @Test
    void getUserIdFromTokenThrowsUserNotFoundExceptionIfUserDoesNotExist() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.getUserIdFromToken("1234")
        );

        // Assert
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void anAuthenticatedUserIsGeneratedWhenTheLoginDetailsAreCorrect() {
        // Arrange
        String username = "mark";
        String password = "supersafe";
        //
        AuthenticationDTO mockedUserLoginDetails = mock(AuthenticationDTO.class);
        when(mockedUserLoginDetails.getUser()).thenReturn(username);
        when(mockedUserLoginDetails.getPassword()).thenReturn(password);
        //
        when(mockedAuthenticateCredentials.attemptLogin(username, password)).thenReturn(1);

        // Act
        var result = sut.loginRequest(mockedUserLoginDetails);

        // Assert
        verify(mockedAuthenticateCredentials).attemptLogin(username, password);
        assertEquals(AuthenticatedUser.class, result.getClass() );
    }

    @Test
    void whenATokenMatchesAnUserIdTrueIsReturnedFromDoesTokenMatchUserId() {
        // Arrange
        int userId = 1;
        String username = "mark";
        String token = "1234-1234-1234";
        //
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(user.getUser()).thenReturn(username);
        when(user.getToken()).thenReturn(token);
        when(user.getUserId()).thenReturn(userId);
        //
        ArrayList<AuthenticatedUser> list = new ArrayList<>();
        list.add(user);
        sut.setUsers(list);

        // Act
        boolean result = sut.doesTokenMatchUserId(token, userId);

        // Assert
        verify(user, times(1)).getUserId();
        assertEquals(true, result);
    }

    @Test
    void whenATokenDoesNotMatchAUserIdFalseIsReturnedFromDoesTokenMatchUserId() {
        // Arrange
        int userId = 1;
        String username = "mark";
        String token = "1234-1234-1234";
        //
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(user.getUser()).thenReturn(username);
        when(user.getToken()).thenReturn(token);
        when(user.getUserId()).thenReturn(userId);
        //
        ArrayList<AuthenticatedUser> list = new ArrayList<>();
        list.add(user);
        sut.setUsers(list);

        // Act
        boolean result = sut.doesTokenMatchUserId(token, userId + 1);

        // Assert
        verify(user, times(1)).getUserId();
        assertEquals(false, result);
    }

}