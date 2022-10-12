package nl.han.dea.markkiepe.spotitube.resources;

import nl.han.dea.markkiepe.spotitube.resources.dto.playlists.PlaylistDTO;
import nl.han.dea.markkiepe.spotitube.services.playlists.PlaylistService;
import nl.han.dea.markkiepe.spotitube.services.tracks.Track;
import nl.han.dea.markkiepe.spotitube.services.tracks.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PlaylistResourceTest {

    private static final String AUTHENTICATION_TOKEN = "1234-1234-1234";
    private static final int PLAYLISTID = 123;
    private static final int TRACKID = 321;

    private PlaylistResource sut;
    private PlaylistService mockedPlaylistService;
    private TrackService mockedTrackService;

    private PlaylistDTO mockedPlaylistDTO;
    private Track mockedTrack;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new PlaylistResource();
        mockedPlaylistService = mock(PlaylistService.class);
        mockedTrackService = mock(TrackService.class);
        sut.setPlaylistService(mockedPlaylistService);
        sut.setTrackService(mockedTrackService);

        mockedPlaylistDTO = mock(PlaylistDTO.class);
        mockedTrack = mock(Track.class);
    }

    @Test
    void whenGettingPlaylistsThePlaylistServiceIsInvoked() {
        // Act
        sut.getPlaylists(AUTHENTICATION_TOKEN);

        // Assert
        verify(mockedPlaylistService, times(1)).getPlaylists(AUTHENTICATION_TOKEN);
    }

    @Test
    void whenAddingPlaylistThePlaylistServiceIsInvoked() {
        // Act
        sut.createPlaylist(AUTHENTICATION_TOKEN, mockedPlaylistDTO);

        // Assert
        verify(mockedPlaylistService, times(1)).createPlaylist(AUTHENTICATION_TOKEN, mockedPlaylistDTO);
    }

    @Test
    void whenDeletingPlaylistThePlaylistServiceIsInvoked() {
        // Act
        sut.deletePlaylist(AUTHENTICATION_TOKEN, PLAYLISTID);

        // Assert
        verify(mockedPlaylistService, times(1)).deletePlaylist(AUTHENTICATION_TOKEN, PLAYLISTID);
    }

    @Test
    void whenModifyingAPlaylistThePlaylistServiceIsInvoked() {
        // Act
        sut.updatePlaylist(AUTHENTICATION_TOKEN, PLAYLISTID, mockedPlaylistDTO);

        // Assert
        verify(mockedPlaylistService, times(1)).editPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID, mockedPlaylistDTO);
    }

    @Test
    void whenGettingTracksInPlaylistTheTrackServiceIsInvoked() {
        // Act
        sut.getTracksInPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID);

        // Assert
        verify(mockedTrackService, times(1)).getTracksInPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID);
    }

    @Test
    void whenAddingTracksToPlaylistTheTrackServiceIsInvoked() {
        // Act
        sut.addTrackToPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID, mockedTrack);

        // Assert
        verify(mockedTrackService, times(1)).addTrackInPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID, mockedTrack);
    }

    @Test
    void whenDeletingTrackFromPlaylistTheTrackServiceIsInvoked() {
        // Act
        sut.deleteTrackFromPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID, TRACKID);

        // Assert
        verify(mockedTrackService, times(1)).deleteTrackInPlaylist(AUTHENTICATION_TOKEN, PLAYLISTID, TRACKID);
    }

}