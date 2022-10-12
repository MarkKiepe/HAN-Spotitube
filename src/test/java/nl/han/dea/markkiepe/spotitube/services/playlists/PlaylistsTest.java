package nl.han.dea.markkiepe.spotitube.services.playlists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistsTest {

    private Playlists sut;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new Playlists();
    }

    @Test
    void gettingPlaylistsReturnsTheCorrectPlaylists() {
        // Arrange
        ArrayList<Playlist> expectedResult = new ArrayList<>();

        // Act
        var result = sut.getPlaylists();

        // Assert
        assertArrayEquals(expectedResult.toArray(), result.toArray());
    }

    @Test
    void addingNewPlaylistDaoToArrayAndGettingPlaylistsReturnsTheCorrectPlaylists() {
        // Arrange
        ArrayList<Playlist> expectedResult = new ArrayList<>();
        Playlist mockedPlaylist = mock(Playlist.class);
        expectedResult.add(mockedPlaylist);

        // Act
        sut.addPlaylist(mockedPlaylist);
        var result = sut.getPlaylists();

        // Assert
        assertArrayEquals(expectedResult.toArray(), result.toArray());
    }

    @Test
    void gettingPlaylistLengthWhileHavingEmptyPlaylistArrayReturnsZero() {
        // Act
        var result = sut.getLength();

        // Assert
        assertEquals(0, result);
    }

    @Test
    void gettingPlaylistLengthWhileHavingPlaylistsReturnsTheCorrectLength() {
        // Arrange
        int setLength = 50;
        sut.setLength(setLength);

        // Act
        var result = sut.getLength();

        // Assert
        assertEquals(setLength, result);
    }
}