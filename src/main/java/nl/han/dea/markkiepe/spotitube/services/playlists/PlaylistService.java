package nl.han.dea.markkiepe.spotitube.services.playlists;

import nl.han.dea.markkiepe.spotitube.datasource.PlaylistMapper;
import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.playlists.PlaylistsDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;
import nl.han.dea.markkiepe.spotitube.resources.dto.playlists.PlaylistDTO;
import nl.han.dea.markkiepe.spotitube.services.AuthenticationManager;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UnauthorizedException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;
import nl.han.dea.markkiepe.spotitube.services.tracks.TrackService;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * This class manages all requests that involve playlists.
 *
 * @see AuthenticationManager
 * @see PlaylistMapper
 * @see TrackService
 *
 * @since 1.0
 * @author Mark Kiepe
 */
public class PlaylistService {

    private static PlaylistMapper playlistMapper;
    private static AuthenticationManager authenticationManager;
    private static TrackService trackService;

    /**
     * Setter for the {@link PlaylistMapper} that is used to communicate with the database.
     * @param playlistMapper {@link PlaylistMapper}
     * @since 1.0
     */
    @Inject
    public void setPlaylistMapper(PlaylistMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }

    /**
     * Setter for the {@link AuthenticationManager} that is used to verify if the given Authentication Token
     * has the required permission to view, delete and/or modify the given playlist.
     * @param authenticationManager {@link AuthenticationManager}
     * @since 1.0
     */
    @Inject
    public void setPlaylistAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Setter for the {@link TrackService} that is used the get certain track related data when it is required
     * in playlist data.
     * @param trackService {@link TrackService}
     * @since 1.0
     */
    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    //

    /**
     * Gets all playlists that an user owns.
     *
     * @see AuthenticationManager
     * @see Playlists
     *
     * @param authenticationToken {@link String} Authentication Token
     * @throws UserNotFoundException When the given Authentication Token does not exist this exception is thrown.
     * @return {@link Playlists}
     * @since 1.0
     */
    public Playlists getPlaylists(String authenticationToken) throws UserNotFoundException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            return getPlaylists(userId);
        }
        throw new UserNotFoundException();
    }

    /**
     * Creates a new playlist for the given user with the given specifications.
     *
     * @see AuthenticationManager
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistDTO {@link PlaylistDTO} containing all data required to create a playlist.
     * @throws UserNotFoundException When the given Authentication Token does not exist this exception is thrown.
     * @return {@link Playlists}
     * @since 1.0
     */
    public Playlists createPlaylist(String authenticationToken, PlaylistDTO playlistDTO) throws UserNotFoundException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            playlistMapper.createPlaylist(userId, playlistDTO.getName());

            // Getting all user's playlists
            return getPlaylists(userId);
        }
        throw new UserNotFoundException();
    }

    /**
     * Deletes a specified playlist from the given user's data.
     *
     * @see AuthenticationManager
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistId {@link Integer} PlaylistId you wish to remove.
     * @throws UnauthorizedException This exception is thrown when a given Authentication Token tries to delete
     * a playlist that is not his.
     * @throws UserNotFoundException This exception is thrown when a given Authentication Token does not exist.
     * @return {@link Playlists}
     * @since 1.0
     */
    public Playlists deletePlaylist(String authenticationToken, int playlistId) throws UnauthorizedException, UserNotFoundException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            if (authenticationManager.userHasPlaylistOwnership(userId, playlistId)) {
                // Deleting playlist
                playlistMapper.deletePlaylist(playlistId);

                // Getting all user's playlists
                return getPlaylists(userId);
            }
            throw new UnauthorizedException();
        }
        throw new UserNotFoundException();
    }

    /**
     * Edits a specified playlist with the given modifications.
     *
     * @see AuthenticationManager
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistId {@link Integer} playlistId of the playlist you wish to modify.
     * @param playlistDTO {@link PlaylistDTO} containing all modification data.
     * @return {@link Playlists}
     * @throws UnauthorizedException This exception is thrown when a given Authentication Token tries to modify
     * a playlist that is not his.
     * @throws UserNotFoundException This exception is thrown when a given Authentication Token does not exist.
     * @since 1.0
     */
    public Playlists editPlaylist(String authenticationToken, int playlistId, PlaylistDTO playlistDTO) throws UnauthorizedException, UserNotFoundException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            if (authenticationManager.userHasPlaylistOwnership(userId, playlistId)) {
                // Editing playlist
                playlistMapper.modifyPlaylist(playlistId, playlistDTO.getName());

                // Getting all user's playlists
                return getPlaylists(userId);
            }
            throw new UnauthorizedException();
        }
        throw new UserNotFoundException();
    }

    // Private Functions

    /**
     * Creates a {@link Playlists} object containing all playlists that belong to the specified userId.
     *
     * @param userId {@link Integer} UserId
     * @return {@link Playlists}
     * @since 1.0
     */
    private Playlists getPlaylists(int userId) {
        Playlists playlists = new Playlists();
        //
        PlaylistsDao playlistsDao = playlistMapper.getPlaylistsFromUserId(userId);
        if (playlistsDao != null) {
            playlists.setLength(playlistsDao.getLength());
            //
            ArrayList<PlaylistDao> playlistDaos = playlistsDao.getPlaylists();
            for (PlaylistDao playlistDao : playlistDaos) {
                Playlist playlist = new Playlist(
                        playlistDao.getId(),
                        playlistDao.getName(),
                        new TracksDao(),
                        playlistDao.getLength()
                );
                playlists.addPlaylist(playlist);
            }
        }
        return playlists;
    }
}
