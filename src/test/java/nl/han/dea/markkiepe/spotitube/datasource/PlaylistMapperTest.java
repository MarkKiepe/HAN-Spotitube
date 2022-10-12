package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistsDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.playlists.PlaylistDoesNotExistException;
import nl.han.dea.markkiepe.spotitube.datasource.util.DatabaseProperties;
import nl.han.dea.markkiepe.spotitube.datasource.util.SqlProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistMapperTest {

    private PlaylistMapper sut;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new PlaylistMapper();
        sut.setDatabaseProperties(new DatabaseProperties());
        sut.setSqlProperties(new SqlProperties());
    }

    @Test
    void sendingIncorrectPlaylistIdThrowsException() {
        // Act
        var result = assertThrows(
                DatabaseErrorException.class,
                () -> sut.getPlaylistOwnerId(-1)
        );

        // Assert
        assertEquals(DatabaseErrorException.class, result.getClass());
    }

    @Test
    void gettingPlaylistsReturnsPlaylistsDao() {
        // Act
        var result = sut.getPlaylistsFromUserId(1);

        // Assert
        assertEquals(PlaylistsDao.class, result.getClass());
    }

    @Test
    void creatingPlaylistReturnsTrue() {
        // Act
        var result = sut.createPlaylist(1, "h");

        // Assert
        assertEquals(true, result);
    }

    @Test
    void deletingPlaylistReturnsTrue() {
        // Act
        var result = sut.deletePlaylist(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void modifyingPlaylistReturnsTrue() {
        // Act
        var result = sut.modifyPlaylist(1, "hi");

        // Assert
        assertEquals(true, result);
    }
}