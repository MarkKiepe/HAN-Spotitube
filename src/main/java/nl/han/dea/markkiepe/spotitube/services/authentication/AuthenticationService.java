package nl.han.dea.markkiepe.spotitube.services.authentication;

import nl.han.dea.markkiepe.spotitube.resources.dto.authentication.AuthenticationDTO;
import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * This class manages all the Authenticated Users.
 *
 * @see TokenGenerator
 * @see AuthenticatedUser
 * @author Mark Kiepe
 * @since 1.0
 */
public class AuthenticationService {

    private static ArrayList<AuthenticatedUser> users = new ArrayList<>();
    private static TokenGenerator generator;
    private static AuthenticateCredentials authenticateCredentials;

    /**
     * Sets the {@link ArrayList} of {@link AuthenticatedUser}.
     * These users are authenticated and can use the program
     *
     * @param users {@link ArrayList} of {@link AuthenticatedUser}.
     * @since 1.0
     */
    public void setUsers(ArrayList<AuthenticatedUser> users) {
        this.users = users;
    }

    /**
     * Sets the {@link TokenGenerator} that is used to generate authentication tokens for new users.
     * @see TokenGenerator
     * @param generator {@link TokenGenerator}
     * @since 1.0
     */
    @Inject
    public void setGenerator(TokenGenerator generator) {
        this.generator = generator;
    }


    /**
     * Sets the {@link AuthenticateCredentials} that is used to check if an user entered the correct log-in details.
     *
     * @see AuthenticateCredentials
     * @param authenticateCredentials {@link AuthenticateCredentials}
     * @since 1.0
     */
    @Inject
    public void setAuthenticateCredentials(AuthenticateCredentials authenticateCredentials) {
        this.authenticateCredentials = authenticateCredentials;
    }

    //

    /**
     * Confirms if the log-in details provided are correct.
     * This method uses the {@link AuthenticateCredentials} class to confirm that the user exists in the database.
     * If the user does not exist the {@link IncorrectCredentialsException} exception is thrown.
     * This exception includes a message of what went wrong while trying to authenticate.
     *
     * If the authentication is successful a new {@link AuthenticatedUser} is created in the
     * {@link AuthenticationService#addUser(int userId, String username)} method.
     * This method returns the Authentication Token in {@link String} format that the client can use to identify themselves.
     *
     * @see TokenGenerator
     * @see AuthenticateCredentials
     * @see AuthenticationDTO
     *
     * @param transferObject {@link AuthenticationDTO} that contains a username and password.
     * @return {@link AuthenticatedUser}
     * @throws IncorrectCredentialsException If the username, or password is incorrect the {@code IncorrectCredentialsException}
     * is thrown.
     * @since 1.0
     */
    public AuthenticatedUser loginRequest(AuthenticationDTO transferObject) {
        int userId = authenticateCredentials.attemptLogin(transferObject.getUser(), transferObject.getPassword());
        if (userId != 0) {
            return addUser(userId, transferObject.getUser());
        }
        throw new IncorrectCredentialsException("Username or password is incorrect.");
    }

    /**
     * Finds and returns the UserId that belongs to the given Token.
     * If the token does not exist because it is invalid a {@link UserNotFoundException} will be thrown.
     *
     * @param token The token the user has to authenticate with.
     * @return The UserId as an {@link Integer}
     * @throws UserNotFoundException If the token is invalid.
     * @since 1.0
     */
    public int getUserIdFromToken(String token) {
        AuthenticatedUser user = getUserFromToken(token);
        if (user != null) {
            return user.getUserId();
        }
        throw new UserNotFoundException();
    }

    /**
     * This method is used to verify if a certain user has this userid that it pretends to have.
     * @param token {@link String} as Authentication Token
     * @param userId {@link Integer} as UserId
     * @return {@link Boolean} whether they match
     * @since 1.0
     */
    public boolean doesTokenMatchUserId(String token, int userId) {
        AuthenticatedUser user = getUserFromToken(token);
        if (user != null) {
            if (user.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }


    // PRIVATE FUNCTIONS

    /**
     * Creates a new Authenticated User and stores it in a private {@link ArrayList}.
     * If the Authenticated User is successfully created a token will be returned where the user can be identified with.
     *
     * @see TokenGenerator
     *
     * @param userId The UserId you want to create an Authentication Account for.
     * @param username The username of the account you are creating an Authentication Account for.
     * @return Returns a {@link AuthenticatedUser} that includes a {@code Token} and a {@Code Username}
     * @since 1.0
     */
    private AuthenticatedUser addUser(int userId, String username) {
        AuthenticatedUser user = new AuthenticatedUser(
                userId,
                username,
                generateUniqueToken()
        );
        users.add(user);
        return user;
    }

    /**
     * Creates a unique token for a user.
     * This method uses the {@link TokenGenerator#generateNewToken()} method from the {@link TokenGenerator} and makes sure the token\
     * is not already in use by another user. If the token is already in use another one will be generated -> the method
     * calling this function does not have to worry about that.
     *
     * @see TokenGenerator
     *
     * @return Unique authentication token for an user.
     * @since 1.0
     */
    private String generateUniqueToken() {
        while (true) {
            String tempToken = generator.generateNewToken();
            if (doesTokenExist(tempToken) == false) {
                return tempToken;
            }
        }
    }

    /**
     * This method checks if a token is already in use by any user.
     * If the token is already in use this method does not return for which user the token is.
     *
     * @implNote
     * If you wish to know to which account a token belongs instead of knowing whether it exists please
     * refer to {@link AuthenticationService#getUserFromToken(String token)} instead.
     *
     * @param token A string token that you want to check if exists.
     * @return Boolean whether the token already exists.
     * @since 1.0
     */
    private boolean doesTokenExist(String token) {
        if (getUserFromToken(token) != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns a reference to an AuthenticatedUser object of which the token belongs to.
     * If the token is invalid (because it is not in use) NULL will be returned.
     * The method calling this has to check if no NULL was returned.
     *
     * @implSpec
     * This method can return NULL. The method calling this has to check if no NULL was returned.
     * If a NULL is returned the token is invalid and does not belong to an user.
     *
     * @implNote
     * If you only wish to know whether the token is taken please use the {@link AuthenticationService#doesTokenExist(String token)}
     * method instead.
     *
     * @param token String token that belongs to the user you want.
     * @return {@link AuthenticatedUser} object of which the token belongs to
     * @since 1.0
     */
     private AuthenticatedUser getUserFromToken(String token) {
        for (AuthenticatedUser user : users) {
            if (token.matches(user.getToken())) {
                return user;
            }
        }
        return null;
    }

}
