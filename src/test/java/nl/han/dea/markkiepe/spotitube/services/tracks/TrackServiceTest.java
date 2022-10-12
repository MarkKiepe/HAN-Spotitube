package nl.han.dea.markkiepe.spotitube.services.tracks;

import nl.han.dea.markkiepe.spotitube.datasource.TracksMapper;
import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TrackDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;
import nl.han.dea.markkiepe.spotitube.services.AuthenticationManager;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UnauthorizedException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackServiceTest {

    private static final String EXISTING_TOKEN = "9213-1245-1239";
    private static final String UNAUTHORIZED_TOKEN = "1241-1242-3124";
    private static final String NOT_EXISTING_TOKEN = "125091927491274";
    private static final int EXISTING_USERID = 124;
    private static final int UNAUTHORIZED_USERID = 142;
    private static final int PLAYLIST_ID = 456;
    private static final int TRACK_ID = 242;

    private TrackService sut;
    private AuthenticationManager mockedAuthenticationManager;
    private TracksMapper mockedTracksMapper;
    private Track mockedTrack;
    private TracksDao mockedTracksDao;
    private ArrayList mockedArrayList;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new TrackService();
        mockedAuthenticationManager = mock(AuthenticationManager.class);
        mockedTracksMapper = mock(TracksMapper.class);
        mockedTrack = mock(Track.class);
        mockedTracksDao = mock(TracksDao.class);
        mockedArrayList = mock(ArrayList.class);
        //
        sut.setAuthenticationManager(mockedAuthenticationManager);
        sut.setTracksMapper(mockedTracksMapper);
        //
        when(mockedAuthenticationManager.getUserIdFromToken(EXISTING_TOKEN)).thenReturn(EXISTING_USERID);
        when(mockedAuthenticationManager.getUserIdFromToken(UNAUTHORIZED_TOKEN)).thenReturn(UNAUTHORIZED_USERID);
        when(mockedAuthenticationManager.getUserIdFromToken(NOT_EXISTING_TOKEN)).thenReturn(0);
        when(mockedAuthenticationManager.userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID)).thenReturn(true);
        when(mockedAuthenticationManager.userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID)).thenReturn(false);
        when(mockedTrack.getId()).thenReturn(TRACK_ID);
        when(mockedTracksMapper.getTracksInPlaylist(PLAYLIST_ID)).thenReturn(mockedTracksDao);
        when(mockedTracksMapper.getTracksNotInPlaylist(PLAYLIST_ID)).thenReturn(mockedTracksDao);
        when(mockedTracksDao.getTracks()).thenReturn(mockedArrayList);
    }

    @Test
    void gettingTracksInPlaylistWithAnNotExistingTokenThrowsUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.getTracksInPlaylist(NOT_EXISTING_TOKEN, PLAYLIST_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void gettingTracksInPlaylistWithAnNotAuthorizedTokenThrowsUnauthorizedException() {
        // Act
        var result = assertThrows(
                UnauthorizedException.class,
                () -> sut.getTracksInPlaylist(UNAUTHORIZED_TOKEN, PLAYLIST_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(UNAUTHORIZED_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID);
        assertEquals(UnauthorizedException.class, result.getClass());
    }

    @Test
    void gettingTracksInPlaylistReturnsTracksObjectAndInvokesTrackMapper() {
        // Act
        var result = sut.getTracksInPlaylist(EXISTING_TOKEN, PLAYLIST_ID);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedAuthenticationManager, atLeastOnce()).userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID);
        verify(mockedTracksMapper, times(1)).getTracksInPlaylist(PLAYLIST_ID);
        assertEquals(Tracks.class, result.getClass());
    }

    @Test
    void gettingTracksNotInPlaylistWithAnNotExistingTokenThrowsUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.getTracksNotInPlaylist(NOT_EXISTING_TOKEN, PLAYLIST_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void gettingTracksNotInPlaylistWithAnNotAuthorizedTokenThrowsUnauthorizedException() {
        // Act
        var result = assertThrows(
                UnauthorizedException.class,
                () -> sut.getTracksNotInPlaylist(UNAUTHORIZED_TOKEN, PLAYLIST_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(UNAUTHORIZED_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID);
        assertEquals(UnauthorizedException.class, result.getClass());
    }

    @Test
    void gettingTracksNotInPlaylistReturnsTracksObjectAndInvokesTrackMapper() {
        // Act
        var result = sut.getTracksNotInPlaylist(EXISTING_TOKEN, PLAYLIST_ID);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedAuthenticationManager, atLeastOnce()).userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID);
        verify(mockedTracksMapper, times(1)).getTracksNotInPlaylist(PLAYLIST_ID);
        assertEquals(Tracks.class, result.getClass());
    }

    @Test
    void addingTrackToPlaylistWithAnNotExistingTokenThrowsUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.addTrackInPlaylist(NOT_EXISTING_TOKEN, PLAYLIST_ID, mockedTrack)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void addingTrackToPlaylistWithAnNotAuthorizedTokenThrowsUnauthorizedException() {
        // Act
        var result = assertThrows(
                UnauthorizedException.class,
                () -> sut.addTrackInPlaylist(UNAUTHORIZED_TOKEN, PLAYLIST_ID, mockedTrack)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(UNAUTHORIZED_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID);
        assertEquals(UnauthorizedException.class, result.getClass());
    }

    @Test
    void addingTrackToPlaylistReturnsTracksObjectAndInvokesTrackMapper() {
        // Act
        var result = sut.addTrackInPlaylist(EXISTING_TOKEN, PLAYLIST_ID, mockedTrack);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedAuthenticationManager, atLeastOnce()).userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID);
        verify(mockedTrack, times(1)).getId();
        verify(mockedTracksMapper, times(1)).addTrackToPlaylist(EXISTING_USERID, PLAYLIST_ID, TRACK_ID);
        assertEquals(Tracks.class, result.getClass());
    }

    @Test
    void deletingTrackFromPlaylistWithAnNotExistingTokenThrowsUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.deleteTrackInPlaylist(NOT_EXISTING_TOKEN, PLAYLIST_ID, TRACK_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void deletingTrackFromPlaylistWithAnNotAuthorizedTokenThrowsUnauthorizedException() {
        // Act
        var result = assertThrows(
                UnauthorizedException.class,
                () -> sut.deleteTrackInPlaylist(UNAUTHORIZED_TOKEN, PLAYLIST_ID, TRACK_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(UNAUTHORIZED_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID);
        assertEquals(UnauthorizedException.class, result.getClass());
    }

    @Test
    void deletingTrackFromPlaylistReturnsTracksObjectAndInvokesTrackMapper() {
        // Act
        var result = sut.deleteTrackInPlaylist(EXISTING_TOKEN, PLAYLIST_ID, TRACK_ID);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedAuthenticationManager, atLeastOnce()).userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID);
        verify(mockedTracksMapper, times(1)).deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);
        assertEquals(Tracks.class, result.getClass());
    }

}