package nl.han.dea.markkiepe.spotitube.datasource.dao.playlists;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistDaoTest {

    private static final int PLAYLIST_ID = 123;
    private static final String PLAYLIST_NAME = "cool playlist";
    private static final int PLAYLIST_DURATION = 321;

    @Test
    void whenCreatingNewPlaylistDaoGettingAllItemsReturnsTheRightItems() {
        // Arrange
        PlaylistDao sut = new PlaylistDao(
                PLAYLIST_ID,
                PLAYLIST_NAME,
                PLAYLIST_DURATION
        );

        // Act
        int returnedId = sut.getId();
        String returnedPlaylistName = sut.getName();
        int returnedDuration = sut.getLength();

        // Assert
        assertEquals(PLAYLIST_ID, returnedId);
        assertEquals(PLAYLIST_NAME, returnedPlaylistName);
        assertEquals(PLAYLIST_DURATION, returnedDuration);
    }

}