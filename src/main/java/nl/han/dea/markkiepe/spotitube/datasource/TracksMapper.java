package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TrackDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Tracks Mapper<br>
 * This class is responsible for retrieving all database information
 * related to Tracks. Retrieved data is put into a {@link TracksDao} that
 * can contain {@link TrackDao}.
 *
 * @see TracksDao
 * @see TrackDao
 *
 * @since 1.0
 * @author Mark Kiepe
 */
public class TracksMapper extends Mapper {

    private static final String GET_TRACKS_IN_PLAYLIST_SCRIPT_NAME = "getTracksInPlaylist";
    private static final String GET_TRACKS_NOT_IN_PLAYLIST_SCRIPT_NAME = "getTracksNotInPlaylist";
    private static final String ADD_TRACK_TO_PLAYLIST_SCRIPT_NAME = "addTrackToPlaylist";
    private static final String DELETE_TRACK_IN_PLAYLIST_SCRIPT_NAME = "deleteTrackInPlaylist";

    // TRACK GETTERS

    /**
     * This method gets all Tracks in a specified playlist.
     *
     * @see TracksMapper#getPlaylistTracks(String, Object...)
     *
     * @param playlistId {@link Integer} PlaylistId
     * @return {@link TracksDao}
     * @throws DatabaseErrorException This exception is thrown when an unexpected error occurs while querying the database.
     * @since 1.0
     */
    public TracksDao getTracksInPlaylist(int playlistId) throws DatabaseErrorException {
        return getPlaylistTracks(GET_TRACKS_IN_PLAYLIST_SCRIPT_NAME, playlistId);
    }

    /**
     * This method gets all Tracks that are not in the specified playlist.
     *
     * @see TracksMapper#getPlaylistTracks(String, Object...)
     *
     * @param playlistId {@link Integer} PlaylistId
     * @return {@link TracksDao}
     * @throws DatabaseErrorException This exception is thrown when an unexpected error occurs while querying the database.
     * @since 1.0
     */
    public TracksDao getTracksNotInPlaylist(int playlistId) throws DatabaseErrorException {
        return getPlaylistTracks(GET_TRACKS_NOT_IN_PLAYLIST_SCRIPT_NAME, playlistId);
    }

    // Track Manipulation

    /**
     * This method adds a new track into a specified playlist.
     *
     * @see Mapper
     *
     * @param ownerId {@link Integer} OwnerId
     * @param playlistId {@link Integer} PlaylistId
     * @param trackId {@link Integer} TrackId
     * @return {@link Boolean} that says if the request was successful.
     * @throws DatabaseErrorException This exception is thrown when an unexpected error occurs while querying the database.
     * @since 1.0
     */
    public boolean addTrackToPlaylist(int ownerId, int playlistId, int trackId) throws DatabaseErrorException {
        try (ResultSet resultSet = queryDatabase(ADD_TRACK_TO_PLAYLIST_SCRIPT_NAME, false, ownerId, playlistId, playlistId, trackId)) {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

    /**
     * Deletes a track from a specified playlist.
     *
     * @see Mapper
     *
     * @param playlistId {@link Integer} PlaylistId
     * @param trackId {@link Integer} TrackId
     * @return {@link Boolean} that says if the request was successful.
     * @throws DatabaseErrorException This exception is thrown when an unexpected error occurs while querying the database.
     * @since 1.0
     */
    public boolean deleteTrackFromPlaylist(int playlistId, int trackId) throws DatabaseErrorException {
        try (ResultSet resultSet = queryDatabase(DELETE_TRACK_IN_PLAYLIST_SCRIPT_NAME, false, playlistId, trackId)) {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

    // Private Functions

    /**
     * Creates a {@link TrackDao} containing all track information
     * @param id {@link Integer}
     * @param title {@link String}
     * @param performer {@link String}
     * @param duration {@link Integer}
     * @param album {@link String}
     * @param playcount {@link Integer}
     * @param publicationDate {@link Date} ignored
     * @param description {@link String}
     * @param offlineAvailable {@link Boolean}
     * @return {@link TrackDao}
     * @since 1.0
     */
    private TrackDao createTrackDao(int id, String title, String performer, int duration, String album, int playcount, Date publicationDate, String description, boolean offlineAvailable) {
        TrackDao trackDao = new TrackDao(
                id,
                title,
                performer,
                duration,
                album,
                playcount,
                publicationDate,
                description,
                offlineAvailable
        );
        return trackDao;
    }

    /**
     * Creates a {@link TracksDao} containing created {@link TrackDao} if the database manages to retrieve tracks.
     *
     * @see Mapper
     * @see Mapper#createPreparedStatement(Connection, String, Object...)
     *
     * @param scriptName {@link String} script name that you wish to execute on the database.
     * @param properties {@link Object} properties that are required to create a prepared statement.
     *                                 Please see ({@link Mapper#createPreparedStatement(Connection, String, Object...) })
     * @return {@link TracksDao}
     * @throws DatabaseErrorException This exception is thrown when an unexpected error occurs while querying the database.
     * @since 1.0
     */
    private TracksDao getPlaylistTracks(String scriptName, Object ... properties) throws DatabaseErrorException {
        try (ResultSet resultSet = queryDatabase(scriptName, true, properties)) {
            TracksDao result = new TracksDao();
            //
            if (isDatabaseResultEmpty(resultSet) == false) {
                while (resultSet.next()) {
                    TrackDao trackDao = createTrackDao(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            null,
                            resultSet.getString(8),
                            resultSet.getBoolean(9)
                    );
                    result.addTrack(trackDao);
                }
            }
            //
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

}
