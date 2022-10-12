package nl.han.dea.markkiepe.spotitube.resources.dto;

import nl.han.dea.markkiepe.spotitube.resources.dto.playlists.PlaylistDTO;
import nl.han.dea.markkiepe.spotitube.services.tracks.Track;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistDTOTest {

    private static final int ID = 123;
    private static final String NAME = "playlistName";
    private static final boolean OWNER = false;
    private static final ArrayList<Track> TRACKS = new ArrayList<>();

    @Test
    void creatingPlaylistDTOFromConstructorAndReturningObjectsReturnsTheRightValues() {
        // Arrange
        PlaylistDTO sut = new PlaylistDTO(
                ID,
                NAME,
                OWNER,
                TRACKS
        );

        // Act
        var idResult = sut.getId();
        var nameResult = sut.getName();
        var ownerResult = sut.isOwner();
        var tracksResult = sut.getTracks();

        // Assert
        assertEquals(idResult, ID);
        assertEquals(nameResult, NAME);
        assertEquals(ownerResult, OWNER);
        assertEquals(tracksResult, TRACKS);
    }

    @Test
    void creatingPlaylistDTOWithEmptyConstructorAndSettingValuesManuallyAndThenReturningThemReturnsTheCorrectValues() {
        // Arrange
        PlaylistDTO sut = new PlaylistDTO();

        // Act
        sut.setId(ID);
        sut.setName(NAME);
        sut.setOwner(OWNER);
        sut.setTracks(TRACKS);
        var idResult = sut.getId();
        var nameResult = sut.getName();
        var ownerResult = sut.isOwner();
        var tracksResult = sut.getTracks();

        // Assert
        assertEquals(idResult, ID);
        assertEquals(nameResult, NAME);
        assertEquals(ownerResult, OWNER);
        assertEquals(tracksResult, TRACKS);
    }

}