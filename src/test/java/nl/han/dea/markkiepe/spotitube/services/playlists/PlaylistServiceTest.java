package nl.han.dea.markkiepe.spotitube.services.playlists;

import nl.han.dea.markkiepe.spotitube.datasource.PlaylistMapper;
import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistsDao;
import nl.han.dea.markkiepe.spotitube.resources.dto.playlists.PlaylistDTO;
import nl.han.dea.markkiepe.spotitube.services.AuthenticationManager;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UnauthorizedException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;
import nl.han.dea.markkiepe.spotitube.services.tracks.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    private static final String EXISTING_TOKEN = "9213-1245-1239";
    private static final String UNAUTHORIZED_TOKEN = "1241-1242-3124";
    private static final String NOT_EXISTING_TOKEN = "125091927491274";
    private static final int EXISTING_USERID = 124;
    private static final int UNAUTHORIZED_USERID = 142;
    private static final String PLAYLIST_NAME = "nicePlaylist";
    private static final int PLAYLIST_ID = 456;

    private PlaylistService sut;
    private PlaylistMapper mockedPlaylistMapper;
    private AuthenticationManager mockedAuthenticationManager;
    private TrackService mockedTrackService;
    private PlaylistDTO mockedPlaylistDTO;
    private PlaylistsDao mockedPlaylistsDao;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new PlaylistService();
        mockedTrackService = mock(TrackService.class);
        mockedPlaylistMapper = mock(PlaylistMapper.class);
        mockedAuthenticationManager = mock(AuthenticationManager.class);
        mockedPlaylistDTO = mock(PlaylistDTO.class);
        mockedPlaylistsDao = mock(PlaylistsDao.class);
        //
        sut.setPlaylistMapper(mockedPlaylistMapper);
        sut.setTrackService(mockedTrackService);
        sut.setPlaylistAuthentication(mockedAuthenticationManager);
        //
        when(mockedAuthenticationManager.getUserIdFromToken(EXISTING_TOKEN)).thenReturn(EXISTING_USERID);
        when(mockedAuthenticationManager.getUserIdFromToken(UNAUTHORIZED_TOKEN)).thenReturn(UNAUTHORIZED_USERID);
        when(mockedAuthenticationManager.getUserIdFromToken(NOT_EXISTING_TOKEN)).thenReturn(0);
        when(mockedAuthenticationManager.userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID)).thenReturn(true);
        when(mockedAuthenticationManager.userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID)).thenReturn(false);
        when(mockedPlaylistDTO.getName()).thenReturn(PLAYLIST_NAME);
        when(mockedPlaylistDTO.getId()).thenReturn(PLAYLIST_ID);
        when(mockedPlaylistMapper.getPlaylistsFromUserId(EXISTING_USERID)).thenReturn(mockedPlaylistsDao);
    }

    @Test
    void gettingUsersWithAnInvalidTokenReturnsAUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.getPlaylists(NOT_EXISTING_TOKEN)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void gettingUsersPlaylistsUsesPlaylistMapperAndReturnsAPlaylistsObject() {
        // Act
        var result = sut.getPlaylists(EXISTING_TOKEN);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedPlaylistMapper, times(1)).getPlaylistsFromUserId(EXISTING_USERID);
        assertEquals(Playlists.class, result.getClass());
    }

    @Test
    void creatingPlaylistWithAnInvalidTokenReturnsAUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.createPlaylist(NOT_EXISTING_TOKEN, mockedPlaylistDTO)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void creatingPlaylistUsesPlaylistMapperAndReturnsAPlaylistsObject() {
        // Act
        var result = sut.createPlaylist(EXISTING_TOKEN, mockedPlaylistDTO);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedPlaylistMapper, times(1)).createPlaylist(EXISTING_USERID, PLAYLIST_NAME);
        assertEquals(Playlists.class, result.getClass());
    }

    @Test
    void deletingPlaylistWithInvalidTokenThrowsUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.deletePlaylist(NOT_EXISTING_TOKEN, PLAYLIST_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void deletingPlaylistWithUnauthorizedUserThrowsUnauthorizedException() {
        // Act
        var result = assertThrows(
                UnauthorizedException.class,
                () -> sut.deletePlaylist(UNAUTHORIZED_TOKEN, PLAYLIST_ID)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(UNAUTHORIZED_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID);
        assertEquals(UnauthorizedException.class, result.getClass());
    }

    @Test
    void deletingPlaylistUsesPlaylistMapperAndReturnsPlaylistObject() {
        // Act
        var result = sut.deletePlaylist(EXISTING_TOKEN, PLAYLIST_ID);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID);
        verify(mockedPlaylistMapper, times(1)).deletePlaylist(PLAYLIST_ID);
        assertEquals(result.getClass(), Playlists.class);
    }

    @Test
    void editingPlaylistWithInvalidTokenThrowsUserNotFoundException() {
        // Act
        var result = assertThrows(
                UserNotFoundException.class,
                () -> sut.editPlaylist(NOT_EXISTING_TOKEN, PLAYLIST_ID, mockedPlaylistDTO)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(NOT_EXISTING_TOKEN);
        assertEquals(UserNotFoundException.class, result.getClass());
    }

    @Test
    void editingPlaylistWithUnauthorizedUserThrowsUnauthorizedException() {
        // Act
        var result = assertThrows(
                UnauthorizedException.class,
                () -> sut.editPlaylist(UNAUTHORIZED_TOKEN, PLAYLIST_ID, mockedPlaylistDTO)
        );

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(UNAUTHORIZED_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(UNAUTHORIZED_USERID, PLAYLIST_ID);
        assertEquals(UnauthorizedException.class, result.getClass());
    }

    @Test
    void editingPlaylistUsesPlaylistMapperAndReturnsPlaylistObject() {
        // Act
        var result = sut.editPlaylist(EXISTING_TOKEN, PLAYLIST_ID, mockedPlaylistDTO);

        // Assert
        verify(mockedAuthenticationManager, times(1)).getUserIdFromToken(EXISTING_TOKEN);
        verify(mockedAuthenticationManager, times(1)).userHasPlaylistOwnership(EXISTING_USERID, PLAYLIST_ID);
        verify(mockedPlaylistMapper, times(1)).modifyPlaylist(PLAYLIST_ID, PLAYLIST_NAME);
        assertEquals(result.getClass(), Playlists.class);
    }
}