package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;
import nl.han.dea.markkiepe.spotitube.datasource.util.DatabaseProperties;
import nl.han.dea.markkiepe.spotitube.datasource.util.SqlProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TracksMapperTest {

    private static final int PLAYLISTID = 1;
    private static final String EXISTING_DATABASE_SCRIPT = "SCRIPT";
    private static final String NOT_EXISTING_DATABASE_SCRIPT = "NOSCRIPT";

    private TracksMapper sut;
    private DatabaseProperties mockedDatabaseProperties;
    private SqlProperties mockedSqlProperties;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new TracksMapper();
        mockedDatabaseProperties = new DatabaseProperties();
        mockedSqlProperties = new SqlProperties();
        sut.setDatabaseProperties(mockedDatabaseProperties);
        sut.setSqlProperties(mockedSqlProperties);
    }

    @Test
    void gettingTracksReturnsTracksDaoObject() {
        // Act
        var result = sut.getTracksInPlaylist(PLAYLISTID);

        // Assert
        assertEquals(TracksDao.class, result.getClass());
    }

    @Test
    void gettingTracksNotInPlaylistReturnsTracksDaoObject() {
        // Act
        var result = sut.getTracksNotInPlaylist(PLAYLISTID);

        // Assert
        assertEquals(TracksDao.class, result.getClass());
    }

    @Test
    void addingTrackToPlaylistReturnsTrue() {
        // Act
        var result = sut.addTrackToPlaylist(1, 1, 1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void deletingTrackFromPlaylistReturnsTrue() {
        // Act
        var result = sut.deleteTrackFromPlaylist(1, 1);

        // Assert
        assertEquals(true, result);
    }


}