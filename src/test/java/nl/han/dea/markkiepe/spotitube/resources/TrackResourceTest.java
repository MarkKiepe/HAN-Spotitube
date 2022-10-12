package nl.han.dea.markkiepe.spotitube.resources;

import nl.han.dea.markkiepe.spotitube.services.tracks.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TrackResourceTest {

    private static final String AUTHENTICATION_TOKEN = "1234-4321-1234";
    private static final int PLAYLIST_ID = 321;

    private TrackResource sut;
    private TrackService mockedTrackService;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new TrackResource();
        mockedTrackService = mock(TrackService.class);
        sut.setTrackService(mockedTrackService);
    }

    @Test
    void whenGettingTracksNotInPlaylistTheTrackServiceIsInvoked() {
        // Act
        sut.getTracksNotInPlaylist(AUTHENTICATION_TOKEN, PLAYLIST_ID);

        // Assert
        verify(mockedTrackService, times(1)).getTracksNotInPlaylist(AUTHENTICATION_TOKEN, PLAYLIST_ID);
    }

}