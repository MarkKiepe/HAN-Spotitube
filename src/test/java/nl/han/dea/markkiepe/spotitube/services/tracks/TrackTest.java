package nl.han.dea.markkiepe.spotitube.services.tracks;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    private static final int ID = 123;
    private static final String TITLE = "super cool song";
    private static final String PERFORMER = "amazing singer";
    private static final int DURATION = 280;
    private static final String ALBUM = "cool album";
    private static final int PLAYCOUNT = 124982;
    private static final Date PUBLICATION_DATE = new Date(System.currentTimeMillis());
    private static final String DESCRIPTION = "pls listen";
    private static final boolean OFFLINE_AVAILABLE = false;

    @Test
    void creatingTrackFromConstructorAndGettingVariablesReturnsTheCorrectVariables() {
        // Arrange
        Track track = new Track(
                ID,
                TITLE,
                PERFORMER,
                DURATION,
                ALBUM,
                PLAYCOUNT,
                PUBLICATION_DATE,
                DESCRIPTION,
                OFFLINE_AVAILABLE
        );

        // Act & Assert
        assertEquals(ID, track.getId());
        assertEquals(TITLE, track.getTitle());
        assertEquals(PERFORMER, track.getPerformer());
        assertEquals(DURATION, track.getDuration());
        assertEquals(ALBUM, track.getAlbum());
        assertEquals(PLAYCOUNT, track.getPlaycount());
        assertEquals(DESCRIPTION, track.getDescription());
        assertEquals(PUBLICATION_DATE, track.getPublicationDate());
        assertEquals(OFFLINE_AVAILABLE, track.getOfflineAvailable());
    }

    @Test
    void creatingEmptyTrackAndSettingValuesManuallyShouldReturnTheCorrectVariables() {
        // Arrange
        Track track = new Track();

        // Act
        track.setId(ID);
        track.setTitle(TITLE);
        track.setPerformer(PERFORMER);
        track.setDuration(DURATION);
        track.setAlbum(ALBUM);
        track.setPlaycount(PLAYCOUNT);
        track.setDescription(DESCRIPTION);
        track.setPublicationDate(PUBLICATION_DATE);
        track.setOfflineAvailable(OFFLINE_AVAILABLE);

        // Act & Assert
        assertEquals(ID, track.getId());
        assertEquals(TITLE, track.getTitle());
        assertEquals(PERFORMER, track.getPerformer());
        assertEquals(DURATION, track.getDuration());
        assertEquals(ALBUM, track.getAlbum());
        assertEquals(PLAYCOUNT, track.getPlaycount());
        assertEquals(DESCRIPTION, track.getDescription());
        assertEquals(PUBLICATION_DATE, track.getPublicationDate());
        assertEquals(OFFLINE_AVAILABLE, track.getOfflineAvailable());
    }

}