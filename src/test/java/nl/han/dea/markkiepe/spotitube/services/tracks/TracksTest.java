package nl.han.dea.markkiepe.spotitube.services.tracks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TracksTest {

    private Tracks sut;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new Tracks();
    }

    @Test
    void gettingTracksWhenThereAreNoTracksReturnsAnEmptyArrayList() {
        // Arrange
        ArrayList<Track> tracks = new ArrayList<>();

        // Act
        var result = sut.getTracks();

        // Assert
        assertEquals(tracks, result);
    }

    @Test
    void addingTrackAndGettingTracksReturnsTheCorrectArrayList() {
        // Arrange
        ArrayList<Track> tracks = new ArrayList<>();
        Track mockedTrack = mock(Track.class);
        tracks.add(mockedTrack);

        // Act
        sut.addTrack(mockedTrack);
        var result = sut.getTracks();

        // Assert
        assertEquals(tracks, result);
    }

}