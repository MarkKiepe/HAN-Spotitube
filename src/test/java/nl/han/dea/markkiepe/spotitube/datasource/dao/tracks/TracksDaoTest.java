package nl.han.dea.markkiepe.spotitube.datasource.dao.tracks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TracksDaoTest {

    private TracksDao sut;

    @BeforeEach
    void setup() {
        // Arrange
        sut = new TracksDao();
    }

    @Test
    void gettingEmptyTracksListReturnsTheCorrectList() {
        // Arrange
        ArrayList<TrackDao> expectedList = new ArrayList<>();

        // Act
        var result = sut.getTracks();

        // Assert
        assertEquals(expectedList, result);
    }

    @Test
    void addingTrackToTracksListReturnsTheCorrectList() {
        // Arrange
        ArrayList<TrackDao> expectedList = new ArrayList<>();
        TrackDao mockedTrackDao = mock(TrackDao.class);
        expectedList.add(mockedTrackDao);

        // Act
        sut.addTrack(mockedTrackDao);
        var result = sut.getTracks();

        // Assert
        assertEquals(expectedList, result);
    }

    @Test
    void gettingTrackLengthFromEmptyListReturnsZero() {
        // Act
        var result = sut.getTracksLength();

        // Assert
        assertEquals(0, result);
    }

    @Test
    void gettingTrackLengthWithTracksReturnsTheCorrectTracksLength() {
        // Arrange
        int trackDuration = 123;
        TrackDao mockedTrackDao = mock(TrackDao.class);
        when(mockedTrackDao.getDuration()).thenReturn(trackDuration);
        sut.addTrack(mockedTrackDao);

        // Act
        var result = sut.getTracksLength();

        // Arrange
        assertEquals(trackDuration, result);
    }

}