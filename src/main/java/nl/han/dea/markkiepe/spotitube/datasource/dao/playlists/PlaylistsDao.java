package nl.han.dea.markkiepe.spotitube.datasource.dao.playlists;

import java.util.ArrayList;

/**
 * Data Access Object<br>
 * Contains all playlists retrieved from the database.
 * @see PlaylistDao
 * @author Mark Kiepe
 * @since 1.0
 */
public class PlaylistsDao {

    private ArrayList<PlaylistDao> playlists = new ArrayList<>();
    private int length = 0;

    /**
     * Inserts a new {@link PlaylistDao} into the {@link ArrayList}.
     * @param playlistDao {@link PlaylistDao}
     * @since 1.0
     */
    public void addPlaylistDao(PlaylistDao playlistDao) {
        this.playlists.add(playlistDao);
        updateLength();
    }

    /**
     * Returns an {@link ArrayList} that contains {@link PlaylistDao} objects in this playlist.
     * @return {@link ArrayList} containing {@link PlaylistDao}'s.
     * @since 1.0
     */
    public ArrayList<PlaylistDao> getPlaylists() {
        return playlists;
    }

    /**
     * Returns the length of this playlist.
     * @return {@link Integer}
     * @since 1.0
     */
    public int getLength() {
        return length;
    }

    // Private Functions

    /**
     * Updates the total length duration of this playlist.
     * @since 1.0
     */
    private void updateLength() {
        int newLength = 0;
        for (PlaylistDao playlistDao : playlists) {
            newLength += playlistDao.getLength();
        }
        length = newLength;
    }
}
