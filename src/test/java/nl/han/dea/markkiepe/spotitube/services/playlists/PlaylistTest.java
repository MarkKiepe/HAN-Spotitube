package nl.han.dea.markkiepe.spotitube.services.playlists;

import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlaylistTest {

    private static final int PLAYLIST_ID = 123;
    private static final String PLAYLIST_NAME = "cool playlist";
    private static final boolean OWNER = true;
    private static final TracksDao TRACKS_DAO = mock(TracksDao.class);
    private static final int PLAYLIST_LENGTH = 124;

    @Test
    void creatingPlaylistDaoFromConstructorReturnsCorrectValues() {
        // Arrange
        Playlist sut = new Playlist(
                PLAYLIST_ID,
                PLAYLIST_NAME,
                TRACKS_DAO,
                PLAYLIST_LENGTH
        );

        // Act & Assert
        assertEquals(PLAYLIST_ID, sut.getId());
        assertEquals(PLAYLIST_NAME, sut.getName());
        assertEquals(OWNER, sut.getOwner());
        assertEquals(TRACKS_DAO, sut.getTracks());
        assertEquals(PLAYLIST_LENGTH, sut.getPlaylistLength());
    }

}