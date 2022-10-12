package nl.han.dea.markkiepe.spotitube.services.authentication;

/**
 * This class houses a few variables for each individual authenticated user of the program.
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class AuthenticatedUser {

    private String token;
    private String user;
    private int userId;

    /**
     * Creates a new Authenticated User.
     *
     * @implNote
     * Token must be a unique token. If multiple authenticated users have the same token they would get access to
     * other users their account.
     *
     * @param userId The unique userId that is used in the database to identify individual users.
     * @param user The username of the user.
     * @param token The unique token the client uses to authenticate who they are.
     * @since 1.0
     */
    public AuthenticatedUser(int userId, String user, String token) {
        this.token = token;
        this.user = user;
        this.userId = userId;
    }

    /**
     * Returns the UserId of this authenticated user. The UserId can be used in the database to identify
     * data that belongs to this user.
     * @return The unique userId this authenticated user has.
     * @since 1.0
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the token used by clients to authenticate who they are.
     * @return The token used by clients to authenticate who they are.
     * @since 1.0
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the username of the user.
     * @return {@link String} username
     * @since 1.0
     */
    public String getUser() {
        return user;
    }

}
