package nl.han.dea.markkiepe.spotitube.datasource.dao.credentials;

import nl.han.dea.markkiepe.spotitube.datasource.CredentialsMapper;

/**
 * Data Access Object for {@link CredentialsMapper}<br>
 * This class is created from the received information of the database.
 *
 * @see CredentialsMapper
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class CredentialsDao {

    private int userId;
    private String password;

    /**
     * Creates a new Dao for a database user.
     * @param userId {@link Integer} userId
     * @param password {@link String} hashed password
     * @since 1.0
     */
    public CredentialsDao(int userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /**
     * Getter for the UserId of this database user.
     * @return {@link Integer} userId
     * @since 1.0
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for the hashed password of this database user.
     * @return {@link String} password
     * @since 1.0
     */
    public String getPassword() {
        return password;
    }

}
