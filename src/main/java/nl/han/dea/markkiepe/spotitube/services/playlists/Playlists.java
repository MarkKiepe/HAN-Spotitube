package nl.han.dea.markkiepe.spotitube.services.playlists;

import java.util.ArrayList;

/**
 * This class contains all the data the client requests
 * @see Playlist
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class Playlists {

    private ArrayList<Playlist> playlists = new ArrayList<>();
    private int length;

    /**
     * Setter for the length of this playlist.
     * @param length {@link Integer}
     * @since 1.0
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Adds a new {@link Playlist} to the {@link ArrayList}.
     * @see Playlist
     * @param playlist {@link Playlist}
     * @since 1.0
     */
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    /**
     * Getter for all the playlists.
     * @see Playlist
     * @return {@link ArrayList< Playlist >}
     * @since 1.0
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Getter for the total length duration of all playlists.
     * @return {@link Integer}
     * @since 1.0
     */
    public int getLength() {
        return length;
    }

}
