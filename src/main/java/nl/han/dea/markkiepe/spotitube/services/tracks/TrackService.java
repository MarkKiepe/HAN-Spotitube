package nl.han.dea.markkiepe.spotitube.services.tracks;

import nl.han.dea.markkiepe.spotitube.datasource.TracksMapper;
import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TrackDao;
import nl.han.dea.markkiepe.spotitube.datasource.dao.tracks.TracksDao;
import nl.han.dea.markkiepe.spotitube.services.AuthenticationManager;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UnauthorizedException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * This class manages all requests that involve tracks.
 * @see AuthenticationManager
 * @see TracksMapper
 * @since 1.0
 * @author Mark Kiepe
 */
public class TrackService {

    private static AuthenticationManager authenticationManager;
    private static TracksMapper tracksMapper;

    /**
     * Setter for the {@link AuthenticationManager} that is used to verify if the given Authentication Token
     * has the required permission to view, delete and/or modify the given track.
     * @param authenticationManager {@link AuthenticationManager}
     * @since 1.0
     */
    @Inject
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Setter for the {@link TracksMapper} that is used to communicate with the database.
     * @param tracksMapper {@link TracksMapper}
     * @since 1.0
     */
    @Inject
    public void setTracksMapper(TracksMapper tracksMapper) {
        this.tracksMapper = tracksMapper;
    }

    //

    /**
     * Creates a {@link Tracks} object that contains {@link Track} objects that belong to the given playlist.
     *
     * @see TracksMapper
     * @see Tracks
     * @see Track
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistId {@link Integer}
     * @throws UserNotFoundException When the given Authentication Token does not exist this exception is thrown.
     * @throws UnauthorizedException When the given Authentication Token does not have the required permissions to
     * view, edit or delete the given track.
     * @return {@link Tracks}
     * @since 1.0
     */
    public Tracks getTracksInPlaylist(String authenticationToken, int playlistId) throws UserNotFoundException, UnauthorizedException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            if (authenticationManager.userHasPlaylistOwnership(userId, playlistId)) {
                return getTracksInPlaylist(playlistId);
            }
            throw new UnauthorizedException();
        }
        throw new UserNotFoundException();
    }

    /**
     * Creates a {@link Tracks} object that contains {@link Track} objects that are not in the given playlist.
     *
     * @see AuthenticationManager
     * @see TracksMapper
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistId {@link Integer} PlaylistId
     * @return {@link Tracks}
     * @throws UserNotFoundException When the given Authentication Token does not exist this exception is thrown.
     * @throws UnauthorizedException When the given Authentication Token does not have the required permissions to
     * view, edit or delete the given track.
     * @since 1.0
     */
    public Tracks getTracksNotInPlaylist(String authenticationToken, int playlistId) throws UserNotFoundException, UnauthorizedException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            if (authenticationManager.userHasPlaylistOwnership(userId, playlistId)) {
                TracksDao tracksDao = tracksMapper.getTracksNotInPlaylist(playlistId);
                return createTracks(tracksDao);
            }
            throw new UnauthorizedException();
        }
        throw new UserNotFoundException();
    }

    /**
     * Adds a new Track to a given Playlist
     *
     * @see AuthenticationManager
     * @see TracksMapper
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistId {@link Integer} playlistId
     * @param track {@link Track} containing track data that you wish to add to the playlist.
     * @return {@link Tracks}
     * @throws UserNotFoundException When the given Authentication Token does not exist this exception is thrown.
     * @throws UnauthorizedException When the given Authentication Token does not have the required permissions to
     * view, edit or delete the given track.
     * @since 1.0
     */
    public Tracks addTrackInPlaylist(String authenticationToken, int playlistId, Track track) throws UserNotFoundException, UnauthorizedException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            if (authenticationManager.userHasPlaylistOwnership(userId, playlistId)) {
                // Adding track to playlist
                tracksMapper.addTrackToPlaylist(userId, playlistId, track.getId());

                // Getting updated tracks in playlist
                return getTracksInPlaylist(playlistId);
            }
            throw new UnauthorizedException();
        }
        throw new UserNotFoundException();
    }

    /**
     * Deletes a given track from the playlist.
     *
     * @see AuthenticationManager
     * @see TracksMapper
     *
     * @param authenticationToken {@link String} Authentication Token
     * @param playlistId {@link Integer} PlaylistId
     * @param trackId {@link Integer} TrackId that you wish to remove from the playlist.
     * @return {@link Tracks}
     * @throws UserNotFoundException When the given Authentication Token does not exist this exception is thrown.
     * @throws UnauthorizedException When the given Authentication Token does not have the required permissions to
     * view, edit or delete the given track.
     * @since 1.0
     */
    public Tracks deleteTrackInPlaylist(String authenticationToken, int playlistId, int trackId) throws UserNotFoundException, UnauthorizedException {
        int userId = authenticationManager.getUserIdFromToken(authenticationToken);
        if (userId > 0) {
            if (authenticationManager.userHasPlaylistOwnership(userId, playlistId)) {
                // Deleting track in playlist
                tracksMapper.deleteTrackFromPlaylist(playlistId, trackId);

                // Getting updated tracks in playlist
                return getTracksInPlaylist(playlistId);
            }
            throw new UnauthorizedException();
        }
        throw new UserNotFoundException();
    }

    // Private Functions

    /**
     * Creates a new {@link Tracks} object from a given {@link TracksDao}
     * @param tracksDao {@link TracksDao} that you wish to convert into {@link Tracks}
     * @return {@link Tracks}
     * @since 1.0
     */
    private Tracks createTracks(TracksDao tracksDao) {
        Tracks tracks = new Tracks();
        //
        if (tracksDao != null) {
            ArrayList<TrackDao> trackDaos = tracksDao.getTracks();
            if (trackDaos.size() > 0) {
                for (TrackDao trackDao : trackDaos) {
                    Track track = new Track(
                            trackDao.getId(),
                            trackDao.getTitle(),
                            trackDao.getPerformer(),
                            trackDao.getDuration(),
                            trackDao.getAlbum(),
                            trackDao.getPlaycount(),
                            trackDao.getPublicationDate(),
                            trackDao.getDescription(),
                            trackDao.getOfflineAvailable()
                    );
                    tracks.addTrack(track);
                }
            }
        }
        return tracks;
    }

    /**
     * Returns a {@link Tracks}s object containing {@link Track} classes that belong to this playlist.
     *
     * @see AuthenticationManager
     * @see TracksMapper
     *
     * @param playlistId {@link Integer} PlaylistId
     * @return {@link Tracks}
     * @since 1.0
     */
    private Tracks getTracksInPlaylist(int playlistId) {
        TracksDao tracksDao = tracksMapper.getTracksInPlaylist(playlistId);
        return createTracks(tracksDao);
    }



}
