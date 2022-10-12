package nl.han.dea.markkiepe.spotitube.datasource.dao.playlists;

/**
 * Data Access Object<br>
 * Contains all the retrieved playlist data from the database.
 * @author Mark Kiepe
 * @since 1.0
 */
public class PlaylistDao {

    private int id;
    private String name;
    private int length;

    /**
     * Constructor to create a new Playlist Dao
     * @param id {@link Integer}
     * @param name {@link String}
     * @param length {@link Integer}
     * @since 1.0
     */
    public PlaylistDao(int id, String name, int length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }

    /**
     * Getter for the id of this playlist
     * @return {@link Integer} id
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name of this playlist
     * @return {@link String} playlist name
     * @since 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the length of this playlist
     * @return {@link Integer}
     * @since 1.0
     */
    public int getLength() {
        return length;
    }

}
