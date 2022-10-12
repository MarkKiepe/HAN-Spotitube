package nl.han.dea.markkiepe.spotitube.services.tracks;

import java.util.ArrayList;

/**
 * Contains all tracks that belong together
 * @see Track
 * @author Mark Kiepe
 * @since 1.0
 */
public class Tracks {

    private ArrayList<Track> tracks = new ArrayList<>();

    /**
     * Adds a new {@link Track} to the collection.
     * @param track {@link Track}
     * @since 1.0
     */
    public void addTrack(Track track) {
        tracks.add(track);
    }

    /**
     * Gets an {@link ArrayList} of {@link Track}s.
     * @return {@link ArrayList}
     * @since 1.0
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
