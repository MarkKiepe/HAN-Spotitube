package nl.han.dea.markkiepe.spotitube.datasource.dao.tracks;

import java.util.ArrayList;

/**
 * Data Access Object<br>
 * This class contains all {@link TracksDao}'s that belong together
 *
 * @see TrackDao
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class TracksDao {

    private ArrayList<TrackDao> tracks = new ArrayList<>();

    /**
     * Adds a new {@link TracksDao} to the {@link ArrayList}.
     * @see TracksDao
     * @param track {@link TrackDao}
     * @since 1.0
     */
    public void addTrack(TrackDao track) {
        tracks.add(track);
    }

    /**
     * Gets all tracks stored in the {@link ArrayList}.
     * @see TracksDao
     * @return {@link ArrayList< TrackDao >}
     * @since 1.0
     */
    public ArrayList<TrackDao> getTracks() {
        return tracks;
    }

    /**
     * Gets the length of all tracks in that are stored in the {@link ArrayList}.
     * @return {@link Integer}
     * @since 1.0
     */
    public int getTracksLength() {
        int total = 0;
        for (TrackDao track : tracks) {
            total += track.getDuration();
        }
        return total;
    }

}
