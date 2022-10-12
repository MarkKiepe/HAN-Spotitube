package nl.han.dea.markkiepe.spotitube.services;

import nl.han.dea.markkiepe.spotitube.datasource.PlaylistMapper;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.services.authentication.AuthenticationService;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationManagerTest {

    private static final String EXISTING_TOKEN = "1234-1234";
    private static final String NOT_EXISTING_TOKEN = "1122";
    private static final String EXCEPTION_TOKEN = "4321-4321";
    private static final int EXISTING_PLAYLISTID = 15;
    private static final int EXISTING_USERID = 123;
    private static final int NOT_EXISTING_PLAYLISTID = 12;
    private static final int EXCEPTION_PLAYLISTID = 1245;


    private AuthenticationManager sut;
    private PlaylistMapper mockedPlaylistMapper;
    private AuthenticationService mockedAuthenticationService;

    @BeforeEach
    void setup() {
        // Arrange
        mockedPlaylistMapper = mock(PlaylistMapper.class);
        mockedAuthenticationService = mock(AuthenticationService.class);
        sut = new AuthenticationManager(mockedPlaylistMapper);
        sut.setAuthenticationService(mockedAuthenticationService);
        //
        when(mockedPlaylistMapper.getPlaylistOwnerId(EXISTING_PLAYLISTID)).thenReturn(EXISTING_USERID);
        when(mockedPlaylistMapper.getPlaylistOwnerId(NOT_EXISTING_PLAYLISTID)).thenReturn(0);
        when(mockedPlaylistMapper.getPlaylistOwnerId(EXCEPTION_PLAYLISTID)).thenThrow(DatabaseErrorException.class);
        when(mockedAuthenticationService.getUserIdFromToken(EXISTING_TOKEN)).thenReturn(EXISTING_USERID);
        when(mockedAuthenticationService.getUserIdFromToken(EXCEPTION_TOKEN)).thenThrow(UserNotFoundException.class);
        when(mockedAuthenticationService.getUserIdFromToken(NOT_EXISTING_TOKEN)).thenReturn(0);
    }

    @Test
    void whenAnCorrectOwnerIdAndPlaylistIdIsGivenAccessIsGiven() {
        // Act
        var result = sut.userHasPlaylistOwnership(EXISTING_USERID, EXISTING_PLAYLISTID);

        // Assert
        verify(mockedPlaylistMapper, times(1)).getPlaylistOwnerId(EXISTING_PLAYLISTID);
        assertEquals(true, result);
    }

    @Test
    void whenAnIncorrectOwnerIdIsGivenThereIsNoAccessGiven() {
        // Act
        var result = sut.userHasPlaylistOwnership(NOT_EXISTING_PLAYLISTID, EXISTING_PLAYLISTID);

        // Assert
        verify(mockedPlaylistMapper, times(1)).getPlaylistOwnerId(EXISTING_PLAYLISTID);
        assertEquals(false, result);
    }

    @Test
    void whenAnUnexpectedExceptionIsThrownThereIsNoAccessGiven() {
        // Act
        var result = sut.userHasPlaylistOwnership(EXISTING_USERID, EXCEPTION_PLAYLISTID);

        // Assert
        verify(mockedPlaylistMapper, times(1)).getPlaylistOwnerId(EXCEPTION_PLAYLISTID);
        assertEquals(false, result);
    }

    @Test
    void whenACorrectTokenIsGivenTheCorrectUserIdIsReturned() {
        // Act
        var result = sut.getUserIdFromToken(EXISTING_TOKEN);

        // Assert
        verify(mockedAuthenticationService, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        assertEquals(EXISTING_USERID, result);
    }

    @Test
    void whenAnIncorrectTokenIsGivenZeroIsReturned() {
        // Act
        var result = sut.getUserIdFromToken(NOT_EXISTING_TOKEN);

        // Assert
        verify(mockedAuthenticationService, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(0, result);
    }

    @Test
    void whenAnUnexpectedExceptionIsThrownZeroIsReturned() {
        // Act
        var result = sut.getUserIdFromToken(EXCEPTION_TOKEN);

        // Assert
        verify(mockedAuthenticationService, times(1)).getUserIdFromToken(EXCEPTION_TOKEN);
        assertEquals(0, result);
    }

}