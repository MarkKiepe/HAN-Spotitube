package nl.han.dea.markkiepe.spotitube.resources.dto;

import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TrackDao;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class TracksDTOTest {

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
    void creatingTrackDaoFromConstructorAndGettingAllValuesReturnsTheCorrectValues() {
        // Arrange
        TrackDao trackDao = new TrackDao(
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
        assertEquals(ID, trackDao.getId());
        assertEquals(TITLE, trackDao.getTitle());
        assertEquals(PERFORMER, trackDao.getPerformer());
        assertEquals(DURATION, trackDao.getDuration());
        assertEquals(ALBUM, trackDao.getAlbum());
        assertEquals(PLAYCOUNT, trackDao.getPlaycount());
        assertEquals(PUBLICATION_DATE, trackDao.getPublicationDate());
        assertEquals(OFFLINE_AVAILABLE, trackDao.getOfflineAvailable());
    }

}