package nl.han.dea.markkiepe.spotitube.datasource.dao.tracks;

import java.util.Date;

/**
 * Data Access Object<br>
 * This class contains data for tracks that have been received from the database.
 * @since 1.0
 * @author Mark Kiepe
 */
public class TrackDao {

    private int id;
    private String title;
    private String performer;
    private int duration;
    private String album;
    private int playcount;
    private Date publicationDate;
    private String description;
    private boolean offlineAvailable;

    /**
     * Constructor to create a new TrackDao.
     * @param id {@link Integer} id of the track
     * @param title {@link String} title of the track
     * @param performer {@link String} name of the performer
     * @param duration {@link Integer} duration of the track
     * @param album {@link String} name of the album
     * @param playcount {@link Integer} amount of times the track has been played
     * @param publicationDate {@link Date} the track was published
     * @param offlineAvailable {@link Boolean} whether the track is available offline
     * @since 1.0
     */
    public TrackDao(int id, String title, String performer, int duration, String album, int playcount, Date publicationDate, String description, boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album = album;
        this.playcount = playcount;
        this.publicationDate = publicationDate;
        this.description = description;
        this.offlineAvailable = offlineAvailable;
    }

    /**
     * Getter for the id of the track
     * @return {@link Integer}
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the title of the track
     * @return {@link String}
     * @since 1.0
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the performer of the track
     * @return {@link String}
     * @since 1.0
     */
    public String getPerformer() {
        return performer;
    }

    /**
     * Getter for the duration of the track
     * @return {@link Integer}
     * @since 1.0
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Getter for the album of the track
     * @return {@link String}
     * @since 1.0
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Getter for the playcount of the track
     * @return {@link Integer}
     * @since 1.0
     */
    public int getPlaycount() {
        return playcount;
    }

    /**
     * Getter for the publication date of the track
     * @return {@link Date}
     * @since 1.0
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * Getter for the description of the track
     * @return {@link String}
     * @since 1.0
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter that says whether the track is available offline
     * @return {@link Boolean}
     * @since 1.0
     */
    public boolean getOfflineAvailable() {
        return offlineAvailable;
    }
}
