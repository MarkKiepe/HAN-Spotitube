package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistsDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.playlists.PlaylistDoesNotExistException;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Playlist Mapper<br>
 * This class is responsible for retrieving all database information related to playlists.
 * Received playlists get put into a {@link PlaylistsDao} which may contain {@link PlaylistDao}('s).
 *
 * @see Mapper
 * @see PlaylistsDao
 * @see PlaylistDao
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class PlaylistMapper extends Mapper {

    private static final String GET_PLAYLIST_OWNER_ID = "getPlaylistOwnerId";
    private static final String GET_PLAYLISTS_SCRIPT_NAME = "getPlaylistsOwnedByUserId";
    private static final String CREATE_PLAYLIST_SCRIPT_NAME = "createNewPlaylist";
    private static final String DELETE_PLAYLIST_SCRIPT_NAME = "deletePlaylist";
    private static final String MODIFY_PLAYLIST_SCRIPT_NAME = "modifyPlaylist";

    // AUTHENTICATION FUNCTIONS

    /**
     * This method returns the ownerId in {@link Integer} format of the requested playlist from its ID.
     * @param playlistId {@link Integer}
     * @return {@link Integer} resembling playlist ownerId.
     * @throws DatabaseErrorException When something goes wrong while querying the database this exception is thrown.
     * @throws PlaylistDoesNotExistException When a playlistId is given that is not linked to an existing
     * playlist this exception is thrown
     * @since 1.0
     */
    public int getPlaylistOwnerId(int playlistId) throws DatabaseErrorException, PlaylistDoesNotExistException {
        try(ResultSet resultSet = queryDatabase(GET_PLAYLIST_OWNER_ID, true, playlistId)) {
            if (isDatabaseResultEmpty(resultSet) == false) {
                while (resultSet.next()) {
                    int ownerId = resultSet.getInt(1);
                    if (ownerId > 0) {
                        return ownerId;
                    }
                }
            }
            throw new PlaylistDoesNotExistException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

    // PLAYLIST GETTERS

    /**
     * Creates a {@lnk PlaylistsDao} containing all retrieved data from the databse.
     *
     * @see PlaylistsDao
     * @see PlaylistDao
     *
     * @param userId userId as an {@link Integer} for which user you want to receive data.
     * @return {@link PlaylistsDao} containing retrieved data.
     * @throws DatabaseErrorException When something goes wrong while querying the database this exception is thrown.
     * @since 1.0
     */
    public PlaylistsDao getPlaylistsFromUserId(int userId) throws DatabaseErrorException {
        return createPlaylistsResponseFromQuery(GET_PLAYLISTS_SCRIPT_NAME, userId);
    }

    // MANIPULATE PLAYLISTS

    /**
     * Creates a new playlist in the database for the given user with the given playlist name.
     * @param userId The UserId of the user that will own the new playlist in {@link Integer} format.
     * @param playlistName The name this new playlist will get in {@link String} format.
     * @return {@link Boolean} that says if the method was successfully executed.
     * @throws DatabaseErrorException When an exception occurs when querying the database this exception is thrown.
     * @since 1.0
     */
    public boolean createPlaylist(int userId, String playlistName) throws DatabaseErrorException {
        try (ResultSet resultSet = queryDatabase(CREATE_PLAYLIST_SCRIPT_NAME, false, userId, playlistName)) {
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

    /**
     * Deletes an existing playlist from the database.
     * @param playlistId The playlist you want to remove from the database in {@link Integer} format.
     * @return {@link Boolean} that says if the method was successfully executed.
     * @throws DatabaseErrorException When an exception occurs while querying the database this exception is thrown.
     * @since 1.0
     */
    public boolean deletePlaylist(int playlistId) throws DatabaseErrorException {
        try (ResultSet resultSet = queryDatabase(DELETE_PLAYLIST_SCRIPT_NAME, false, playlistId, playlistId)) {
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

    /**
     * Modifies an existing playlist and sets the new data.
     * @param playlistId The playlistId of the playlist you want to modify in {@link Integer} format.
     * @param newPlaylistName The new name you want to give this playlist in {@link String} format.
     * @return {@link Boolean} that says if the method was successfully executed.
     * @throws DatabaseErrorException When an exception occurs while querying the database this exception is thrown.
     * @since 1.0
     */
    public boolean modifyPlaylist(int playlistId, String newPlaylistName) throws DatabaseErrorException {
        try (ResultSet resultSet = queryDatabase(MODIFY_PLAYLIST_SCRIPT_NAME, false, newPlaylistName, playlistId)) {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DatabaseErrorException();
    }

    // PRIVATE FUNCTIONS

    /**
     * Creates a new {@link PlaylistDao}
     *
     * @see PlaylistDao
     *
     * @param playlistId {@link Integer}
     * @param playlistName {@link String}
     * @param playlistLength {@link Integer}
     * @return {@link PlaylistDao}
     *
     * @since 1.0
     */
    private PlaylistDao createPlaylistDao(int playlistId, String playlistName, int playlistLength) {
        return new PlaylistDao(
                playlistId,
                playlistName,
                playlistLength
        );
    }

    /**
     * Queries the database with a given script name and creates a new {@link PlaylistsDao} from the retrieved data.
     *
     * @see Mapper#queryDatabase(String, boolean, Object...)
     * @see Mapper#createPreparedStatement(Connection, String, Object...)
     *
     * @param scriptName The name of the script you wish to execute in {@link String} format.
     * @param properties The properties you need for executing the query ({@link Object}).
     * @return {@link PlaylistsDao}
     * @throws DatabaseErrorException When an exception occurs while querying the database this exception is thrown.
     * @since 1.0
     */
    private PlaylistsDao createPlaylistsResponseFromQuery(String scriptName, Object ... properties)
        throws DatabaseErrorException
    {
        PlaylistsDao result = new PlaylistsDao();
        //
        try(ResultSet resultSet = queryDatabase(scriptName, true, properties)) {
            if (isDatabaseResultEmpty(resultSet) == false) {

                while (resultSet.next()) {
                    PlaylistDao playlistDao = createPlaylistDao(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3)
                    );
                    result.addPlaylistDao(playlistDao);
                }

            }
        } catch(Exception e) {
            // Something went wrong in database
            e.printStackTrace();
            throw new DatabaseErrorException();
        }
        //
        return result;
    }

}
