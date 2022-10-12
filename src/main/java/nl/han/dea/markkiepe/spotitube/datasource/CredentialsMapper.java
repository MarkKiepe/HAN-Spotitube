package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.dao.credentials.CredentialsDao;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.credentials.AccountDoesNotExistException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.datasource.util.DatabaseProperties;
import nl.han.dea.markkiepe.spotitube.datasource.util.SqlProperties;

import java.sql.*;

/**
 * Credentials Mapper<br>
 * This class is responsible for getting credential data from the database and converting it
 * to {@link CredentialsDao} Data Access Objects so it can be used by other classes.
 *
 * @see Mapper
 * @see DatabaseProperties
 * @see SqlProperties
 * @see CredentialsDao
 * @see DatabaseErrorException
 * @see AccountDoesNotExistException
 *
 * @author Mark Kiepe
 * @since 1.0
 */
public class CredentialsMapper extends Mapper {

    private static final String GET_PASSWORD_SCRIPT_NAME = "getAccountHashedPassword";

    /**
     * This method gets the requested user info from the database.<br>
     * This method requires an existing {@link String} username in the database.
     * If there are no accounts with this username in the database an {@link AccountDoesNotExistException} exception is thrown.<br>
     * If the method manages to successfully retrieve data from the database a {@link CredentialsDao} object is created and returned.
     * <br><br>
     * If for whatever reason this method crashes while trying to execute the script an {@link DatabaseErrorException} exception is thrown.
     *
     * @see Mapper
     * @see CredentialsDao
     * @see AccountDoesNotExistException
     * @see DatabaseErrorException
     *
     * @param username {@link String} username of the account you want to receive data from.
     * @return When a successful request is done a {@link CredentialsDao} class is returned.
     * @throws AccountDoesNotExistException When the username does not exist in the database this exception is returned. Access
     * to the system is not given.
     * @throws DatabaseErrorException When an unexpected exception is thrown this exception is returned. Access to the
     * system is not given.
     *
     * @since 1.0
     */
    public CredentialsDao getUserInfo(String username)
            throws AccountDoesNotExistException, DatabaseErrorException
    {
        try (ResultSet result = queryDatabase(GET_PASSWORD_SCRIPT_NAME, true, username);)
        {
            if (isDatabaseResultEmpty(result) == false) {

                int userId = 0;
                String hashedPassword = null;
                //
                while (result.next()) {
                    userId = result.getInt("userId");
                    hashedPassword = result.getString("password");
                }

                if (userId != 0 && hashedPassword != null) {
                    CredentialsDao credentials = new CredentialsDao(
                            userId,
                            hashedPassword
                    );
                    return credentials;
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseErrorException();
        }
        throw new AccountDoesNotExistException();
    }
}
