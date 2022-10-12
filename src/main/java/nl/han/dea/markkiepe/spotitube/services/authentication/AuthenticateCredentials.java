package nl.han.dea.markkiepe.spotitube.services.authentication;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.datasource.CredentialsMapper;
import nl.han.dea.markkiepe.spotitube.datasource.dao.credentials.CredentialsDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.credentials.AccountDoesNotExistException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;

import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * This class is responsible for confirming whether given credentials are correct.
 * The class uses both {@link String} checking to see if the length is valid, and the {@link CredentialsMapper}
 * to check if the requested user actually exists in the database.
 * <br>
 * This class also has hashing functionality to ensure there are no plain-text passwords in the database.
 *
 * @see CredentialsMapper
 * @see CredentialsDao
 * @see DigestUtils#sha3_512Hex(String toHash)
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class AuthenticateCredentials {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 35;
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 128;

    private CredentialsMapper credentialsMapper;

    /**
     * Setter for the {@link CredentialsMapper} that is being used in this class.
     * @param credentialsMapper {@link CredentialsMapper}
     * @since 1.0
     */
    @Inject
    public void setCredentialsMapper(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    /**
     * This method will determine whether the user has entered the correct log-in details.
     * If the user has entered the correct details an {@link Integer} resembling the user's UserId will be returned.
     * This UserId will be used to make the user standalone from all other users.
     * <br><br>
     * This method checks if the credential input is correct
     * (see {@link AuthenticateCredentials#isCredentialInputAccepted(String, String)} for more info)
     * and if the password matches an existing account from the database.
     * <br><br>
     * If the log-in for whatever reason is not accepted a {@link IncorrectCredentialsException} is thrown.
     * This exception will include the reason for the failed authentication.
     * <br>
     *
     * @see AuthenticateCredentials#isCredentialInputAccepted(String, String)
     * @see CredentialsMapper
     * @see CredentialsDao
     *
     * @param username {@link String} entered username by client.
     * @param enteredPassword {@link String} entered password by client/
     * @return {@link Integer} resembling a UserId for the authenticated user.
     * @throws IncorrectCredentialsException When the authentication is failed. Exception includes reason for
     * failed Authentication.
     * @since 1.0
     */
    public int attemptLogin(String username, String enteredPassword) throws IncorrectCredentialsException {
        // Checking if credential input meets required criteria
        if (isCredentialInputAccepted(username, enteredPassword)) {
            CredentialsDao userCredentials;
            try {
                // Getting CredentialsDao from database
                userCredentials = credentialsMapper.getUserInfo(username);
            }
            catch(AccountDoesNotExistException e) {
                // Incorrect Credentials
                throw new IncorrectCredentialsException("Username or password is incorrect.");
            }
            catch(DatabaseErrorException e) {
                // Database Error
                throw new IncorrectCredentialsException("Internal Server Error.");
            }
            //
            if (userCredentials != null) {
                if (isPasswordCorrect(enteredPassword, userCredentials.getPassword())) {
                    return userCredentials.getUserId();
                }
            }
        }
        throw new IncorrectCredentialsException("Username or password is incorrect.");
    }

    // PRIVATE FUNCTIONS

    /**
     * This method checks if the input credentials from the client meet the set criteria.
     * The method only checks the {@link String}s their length not whether they are correct.
     * If you wish to know whether the {@link String}s are correct please see
     * {@link AuthenticateCredentials#isPasswordCorrect(String, String)}
     *
     * @see String
     * @see AuthenticateCredentials#isPasswordCorrect(String, String)
     *
     * @param username {@link String} username
     * @param enteredPassword {@link String} entered password
     * @return {@link Boolean} whether the credentials meet the set criteria or not.
     * @since 1.0
     */
    private boolean isCredentialInputAccepted(String username, String enteredPassword) {
        int usernameLength = username.length();
        int passwordLength = enteredPassword.length();

        if (usernameLength >= MIN_USERNAME_LENGTH && usernameLength <= MAX_USERNAME_LENGTH) {
            if (passwordLength >= MIN_PASSWORD_LENGTH && passwordLength <= MAX_PASSWORD_LENGTH) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the input of the plain-text {@link String} entered password is equal to the
     * hashed {@link String} database password.
     * <br><br>
     * This method hashes the plain-text entered password to confirm they are equal.
     * To see how they are converted please see {@link AuthenticateCredentials#hashPassword(String)}.
     *
     * @see AuthenticateCredentials#hashPassword(String)
     *
     * @implSpec
     * This method requires the hashed {@link String} database password.
     * The database password has to be hashed. If the database password is not hashed false will always be returned.
     * <br>
     * However, the {@link String} entered password has to be plain-text.
     * <br>
     * @param enteredPassword {@link String} entered password in plain-text.
     * @param databasePassword {@link String} hashed database password
     * @return {@link Boolean} whether the passwords match
     * @since 1.0
     */
    private boolean isPasswordCorrect(String enteredPassword, String databasePassword) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        if (hashedEnteredPassword != null) {
            if (hashedEnteredPassword.matches(databasePassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hashes a plain-text {@link String} password to the {@code SHA3-512} hash.<br>
     *
     * @implSpec
     * The entered password has to be unhashed in plain-text.
     *
     * @see DigestUtils#sha3_512Hex(String)
     *
     * @param enteredPassword {@link String} plain-text password.
     * @return hashed {@link String} password.
     * @since 1.0
     */
    private String hashPassword(String enteredPassword) {
        String hash = DigestUtils.sha3_512Hex(enteredPassword);
        return hash;
    }
}
