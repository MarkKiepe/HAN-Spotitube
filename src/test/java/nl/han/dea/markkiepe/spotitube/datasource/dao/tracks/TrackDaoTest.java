package nl.han.dea.markkiepe.spotitube.datasource.dao.tracks;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrackDaoTest {

    private final static int ID = 123;
    private final static String TITLE = "cool track";
    private final static String PERFORMER = "artistname";
    private final static int DURATION = 112233;
    private final static String ALBUM = "welcome";
    private final static int PLAYCOUNT = 5;
    private final static Date PUBLICATION_DATE = null;
    private final static String DESCRIPTION = "very cool track!";
    private final static boolean OFFLINE_AVAILABLE = true;

    @Test
    void whenNewTrackDaoIsCreatedTheCorrectValuesAreReturned() {
        // Arrange
        TrackDao sut = new TrackDao(
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

        // Act
        var idResult = sut.getId();
        var titleResult = sut.getTitle();
        var performerResult = sut.getPerformer();
        var durationResult = sut.getDuration();
        var albumResult = sut.getAlbum();
        var playcountResult = sut.getPlaycount();
        var publicationDateResult = sut.getPublicationDate();
        var descriptionResult = sut.getDescription();
        var offlineAvailableResult = sut.getOfflineAvailable();

        // Assert
        assertEquals(ID, idResult);
        assertEquals(TITLE, titleResult);
        assertEquals(PERFORMER, performerResult);
        assertEquals(DURATION, durationResult);
        assertEquals(ALBUM, albumResult);
        assertEquals(PLAYCOUNT, playcountResult);
        assertEquals(PUBLICATION_DATE, publicationDateResult);
        assertEquals(DESCRIPTION, descriptionResult);
        assertEquals(OFFLINE_AVAILABLE, offlineAvailableResult);
    }

}