package nl.han.dea.markkiepe.spotitube.services;

import nl.han.dea.markkiepe.spotitube.datasource.PlaylistMapper;
import nl.han.dea.markkiepe.spotitube.services.authentication.AuthenticationService;

import javax.inject.Inject;

/**
 * Authentication Manager<br>
 * Uses the {@link AuthenticationService} and combines new methods
 * so other classes can verify if certain users have access to certain
 * items.
 *
 * @see AuthenticationService
 *
 * @since 1.0
 * @author Mark Kiepe
 */
public class AuthenticationManager {

    private static PlaylistMapper playlistMapper;
    private static AuthenticationService authenticationService;

    /**
     * Constructor for this class
     * @param playlistMapper {@link PlaylistMapper}
     * @since 1.0
     */
    @Inject
    public AuthenticationManager(PlaylistMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }

    /**
     * Setter for the {@link AuthenticationService} that is used to validate
     * certain permissions for certain users.
     * @param authenticationService {@link AuthenticationService}
     * @since 1.0
     */
    @Inject
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //

    /**
     * This method checks if a certain userId has permission to view, edit
     * and/or delete a certain playlist.
     * @param userId {@link Integer}
     * @param playlistId {@link Integer}
     * @return {@link Boolean}
     * @since 1.0
     */
    public boolean userHasPlaylistOwnership(int userId, int playlistId) {
        int ownerId = getPlaylistOwnerId(playlistId);
        if (userId > 0 && ownerId > 0) {
            if (ownerId == userId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts an Authentication Token into an UserId.
     *
     * @see AuthenticationService
     *
     * @implNote
     * If the Authentication Token is invalid 0 is returned.
     * Zero is not a valid UserId and should not get access the system.<br>
     * If zero is ignored the {@code userHasPlaylistOwnership} will catch this and
     * return false by default.
     *
     * @param authenticationToken {@link String} Authentication Token
     * @return {@link Integer} UserId
     * @since 1.0
     */
    public int getUserIdFromToken(String authenticationToken) {
        try {
            int userId = authenticationService.getUserIdFromToken(authenticationToken);
            if (userId > 0) {
                return userId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //

    /**
     * Gets the Owner id of a given Playlist.
     *
     * @see PlaylistMapper
     *
     * @implNote
     * This method returns 0 if an exception occurs or if the playlist does
     * not exist/cannot be found. When 0 is returned no access should be given
     * to the system.
     *
     * @param playlistId {@link Integer} playlistId you want the ownerId of
     * @return {@link Integer}
     * @since 1.0
     */
    private int getPlaylistOwnerId(int playlistId) {
        try {
            int ownerId = playlistMapper.getPlaylistOwnerId(playlistId);
            if (ownerId >= 0) {
                return ownerId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
