package nl.han.dea.markkiepe.spotitube.services.playlists;

import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;

/**
 * This class contains the data the client requests.
 * @since 1.0
 * @author Mark Kiepe
 */
public class Playlist {

    private int id;
    private String name;
    private boolean owner;
    private TracksDao tracks;
    private int playlistLength;

    /**
     * Constructor to create a new Playlist
     * @see TracksDao
     * @param id {@link Integer} playlist id
     * @param name {@link String} playlist name
     * @param tracks {@link TracksDao} contains all tracks in this playlist
     * @param playlistLength {@link Integer} contains the length of the playlist
     * @since 1.0
     */
    public Playlist(int id, String name, TracksDao tracks, int playlistLength) {
        this.id = id;
        this.name = name;
        this.owner = true;
        this.tracks = tracks;
        this.playlistLength = playlistLength;
    }

    /**
     * Getter for the playlist id
     * @return {@link Integer}
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the playlist name
     * @return {@link String}
     * @since 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that says if user is owner of playlist
     * @return {@link Boolean}
     * @since 1.0
     */
    public boolean getOwner() {
        return owner;
    }

    /**
     * Getter for the tracks in the playlist
     * @return {@link TracksDao}
     * @since 1.0
     */
    public TracksDao getTracks() {
        return tracks;
    }

    /**
     * Getter for the length of the playlist
     * @return {@link Integer}
     * @since 1.0
     */
    public int getPlaylistLength() {
        return playlistLength;
    }
}
