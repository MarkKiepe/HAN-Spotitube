package nl.han.dea.markkiepe.spotitube.services.tracks;

import java.util.Date;

/**
 * This class contains track information
 * @since 1.0
 * @author Mark Kiepe
 */
public class Track {

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
     * Creates a new instance of this class
     * @deprecated
     * @since 1.0
     */
    public Track() {
    }

    /**
     * Constructor to create a new Track
     * @param id {@link Integer} id of the track
     * @param title {@link String} title of the track
     * @param performer {@link String} name of the performer
     * @param duration {@link Integer} duration of the track
     * @param album {@link String} name of the album
     * @param playcount {@link Integer} amount of times the track has been played
     * @param publicationDate {@link Date} the track was published
     * @param description {@link String} description of the track
     * @param offlineAvailable {@link Boolean} whether the track is available offline
     * @since 1.0
     */
    public Track(int id, String title, String performer, int duration, String album, int playcount, Date publicationDate, String description, boolean offlineAvailable) {
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

    /**
     * Setter that sets the id of this track
     * @param id {@link Integer}
     * @since 1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter that sets the id of this title
     * @param title {@link String}
     * @since 1.0
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter that sets the performer of this track
     * @param performer {@link String}
     * @since 1.0
     */
    public void setPerformer(String performer) {
        this.performer = performer;
    }

    /**
     * Setter that sets the duration of this track
     * @param duration {@link Integer}
     * @since 1.0
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Setter that sets the album name of this track
     * @param album {@link String}
     * @since 1.0
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Setter that sets the playcount of this track
     * @param playcount {@link Integer}
     * @since 1.0
     */
    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    /**
     * Setter that sets the publication date of this track
     * @param publicationDate {@link Date}
     * @since 1.0
     */
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Setter that sets the description of this track
     * @param description {@link String}
     * @since 1.0
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter that sets the offline available of this track
     * @param offlineAvailable {@link Boolean}
     * @since 1.0
     */
    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }
}
