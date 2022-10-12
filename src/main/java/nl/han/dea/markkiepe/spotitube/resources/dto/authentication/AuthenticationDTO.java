package nl.han.dea.markkiepe.spotitube.resources.dto.authentication;

import nl.han.dea.markkiepe.spotitube.services.authentication.AuthenticationService;
import nl.han.dea.markkiepe.spotitube.resources.AuthenticationResource;

/**
 * Data Transfer Object for the {@link AuthenticationResource}.<br>
 * The Resource receives this class when an user is trying to log into the system.
 *
 * @implNote
 * This class is made when a log-in request is received.
 * Account details in this class are not confirmed and could be wrong.
 *
 * @see AuthenticationService
 * @see AuthenticationResource
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class AuthenticationDTO {

    private String user;
    private String password;

    /**
     * Constructor.
     * Creates a new instance of this class.
     *
     * @deprecated
     * @since 1.0
     */
    public AuthenticationDTO() {
    }

    /**
     * Constructor.
     * Creates a new instance of this class.
     * Uses a {@link String} for the username and a {@link String} for the password.
     *
     * @param user {@link String} username
     * @param password {@link String} password
     * @since 1.0
     */
    public AuthenticationDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Getter for the username of this attempted login account.
     * @return {@link String} username
     * @since 1.0
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the password of this attempted login account.
     * @return {@link String} username
     * @since 1.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the username of this attempted login account.
     * @param user {@link String} username
     * @since 1.0
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Setter for password of this attempted login account.
     * @param password {@link String} password
     * @since 1.0
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
