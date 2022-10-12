package nl.han.dea.markkiepe.spotitube.datasource.dao.playlists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistsDaoTest {

    private PlaylistsDao sut;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new PlaylistsDao();
    }

    @Test
    void returningPlaylistsReturnsTheCorrectPlaylists() {
        // Arrange
        ArrayList<PlaylistDao> expectedResult = new ArrayList<>();

        // Act
        var result = sut.getPlaylists();

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void addingPlaylistAndReturningPlaylistsReturnsTheCorrectPlaylists() {
        // Arrange
        ArrayList<PlaylistDao> expectedResult = new ArrayList<>();
        PlaylistDao mockedPlaylistDao = mock(PlaylistDao.class);
        expectedResult.add(mockedPlaylistDao);

        // Act
        sut.addPlaylistDao(mockedPlaylistDao);
        var result = sut.getPlaylists();

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void gettingPlaylistLengthWhenHavingNoPlaylistsReturnsZero() {
        // Act
        var result = sut.getLength();

        // Assert
        assertEquals(result, 0);
    }

    @Test
    void gettingPlaylistLengthWhenHavingPlaylistReturnsTheCorrectLength() {
        // Arrange
        int playlistLength = 5;
        PlaylistDao mockedPlaylistDao = mock(PlaylistDao.class);
        when(mockedPlaylistDao.getLength()).thenReturn(playlistLength);

        // Act
        sut.addPlaylistDao(mockedPlaylistDao);
        var result = sut.getLength();

        // Assert
        assertEquals(playlistLength, result);

    }

}