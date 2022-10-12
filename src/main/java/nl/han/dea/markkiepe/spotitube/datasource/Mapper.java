package nl.han.dea.markkiepe.spotitube.datasource;

import nl.han.dea.markkiepe.spotitube.datasource.exceptions.DatabaseErrorException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.SqlScriptDoesntExistException;
import nl.han.dea.markkiepe.spotitube.datasource.exceptions.WrongPreparedStatementType;
import nl.han.dea.markkiepe.spotitube.datasource.util.DatabaseProperties;
import nl.han.dea.markkiepe.spotitube.datasource.util.SqlProperties;

import javax.inject.Inject;
import java.sql.*;

/**
 * This class contains the general data that every mapper requires.
 * @author Mark Kiepe
 * @since 1.0
 */
public class Mapper {

    protected static DatabaseProperties databaseProperties;
    protected static SqlProperties sqlProperties;

    /**
     * Setter for the database properties.
     * @param databaseProperties {@link DatabaseProperties}
     * @since 1.0
     */
    @Inject
    protected void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    /**
     * Setter for the SQL Script Properties
     * @param sqlProperties {@link SqlProperties}
     * @since 1.0
     */
    @Inject
    protected void setSqlProperties(SqlProperties sqlProperties) {
        this.sqlProperties = sqlProperties;
    }

    /**
     * This method checks if the ResultSet from the database returned an empty set or not.
     *
     * @implNote
     * When calling this method a {@code Try Catch} should surround this method.
     *
     * @param result {@link ResultSet}
     * @return {@link Boolean}
     * @throws SQLException When checking if the ResultSet is empty a SQLException can occur.
     * @since 1.0
     */
    protected boolean isDatabaseResultEmpty(ResultSet result) throws SQLException {
        if (result != null && result.isBeforeFirst()) {
            return false;
        }
        return true;
    }

    /**
     * This method creates a connection with the database.<br>
     * The database that is being connected to is received from the {@link DatabaseProperties#getConnectionString()}.<br>
     * The method uses a {@link PreparedStatement} when executing the SQL Script for additional security.
     * <br><br>
     * If for whatever reason this method crashes while trying to execute the script an {@link DatabaseErrorException} exception is thrown.
     * This method always closes the connection if this exception is thrown. However, if everything goes correctly
     * the returned {@link ResultSet} does not get closed automatically. This has to be closed manually or this method has
     * to be called in a {@code Try-With-Resources}.
     *
     * @implNote
     * {@link ResultSet} Must be closed after use {@code ResultSet.close();}.<br>
     * Or this method has to be called in a {@code Try-With-Resources}
     *
     * @see DatabaseProperties
     * @see Mapper#createPreparedStatement(Connection, String, Object...)
     * @see PreparedStatement
     * @see ResultSet
     *
     * @param sqlScriptName Name of the script you wish to execute in {@link String} format.
     * @param expectsResults {@link Boolean} that says whether you want a ResultSet as response.
     * @param scriptParameters All parameters you wish to use in the SQL Script.
     *
     * @return When a successful request is done a {@link ResultSet} with the retrieved data is returned.
     *
     * @throws DatabaseErrorException When an unexpected exception is thrown this exception is returned. Access to the
     * system is not given.
     *
     * @since 1.0
     */
    protected ResultSet queryDatabase(String sqlScriptName, boolean expectsResults, Object ... scriptParameters)
            throws DatabaseErrorException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        //
        try {
            connection = DriverManager.getConnection(databaseProperties.getConnectionString());
            preparedStatement = createPreparedStatement(connection, sqlScriptName, scriptParameters);
            if (expectsResults == true) {
                result = preparedStatement.executeQuery();
            } else {
                preparedStatement.executeUpdate();
            }
        }
        catch(Exception e) {
            // Something went wrong while querying database
            // Closing items
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // Handling error
            e.printStackTrace();
            throw new DatabaseErrorException();
        }
        return result;
    }


    /**
     * This method is responsible for creating prepared statements for all mappers.<br>
     * When calling this method you are required to have a {@link Connection} with an existing database and
     * to have a valid {@code Script Name} for the script you wish to execute.<br>
     * After providing these parameters you can add all the values you need to have replaced in your {@code SQL Script}.
     * These values will be set in the script in the same order as provided. When you provide a variable that is not allowed
     * an {@link WrongPreparedStatementType} will be thrown.
     * <br><br>
     * If everything is set correctly a {@link PreparedStatement} will be returned that can be executed.
     *
     * @implSpec
     * This method requires the following:
     * <ol>
     *     <li>
     *         {@link Connection} to the database
     *     </li>
     *     <li>
     *         {@link String} with the name of the script you want to execute
     *     </li>
     *     <li>
     *         The same amount of properties with the same datatype as requested by the script.<br>
     *         The following datatypes are allowed:
     *         <ul>
     *             <li>
     *                 {@link String}
     *             </li>
     *             <li>
     *                 {@link Integer}
     *             </li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * @see SqlProperties
     *
     * @param connection {@link Connection} with an active database.
     * @param scriptName {@link String} with the name of the script you wish to execute.
     * @param properties Properties that you wish to replace in the script.
     * @return {@link PreparedStatement}
     *
     * @throws SQLException When something goes wrong while making the {@link PreparedStatement}.
     * @throws SqlScriptDoesntExistException When the script name provided does not exist.
     * @throws ClassCastException When something goes wrong while casting {@link Object} to the required class.
     * @throws WrongPreparedStatementType When an incorrect datatype is provided in properties.
     *
     * @since 1.0
     */
    protected PreparedStatement createPreparedStatement(Connection connection, String scriptName, Object ... properties)
            throws SQLException, SqlScriptDoesntExistException, ClassCastException, WrongPreparedStatementType
    {
        // Getting requested script from SQL Properties File.
        String script = sqlProperties.getSqlScript(scriptName);

        // Creating prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(script);

        // Setting prepared statement properties
        for (int propertyIndex = 0; propertyIndex < properties.length; propertyIndex++) {
            Object property = properties[propertyIndex];
            int parameterIndex = propertyIndex + 1;
            //
            if (property.getClass() == String.class) {
                String value = (String) property;
                preparedStatement.setString(parameterIndex, value);

            } else if (property.getClass() == Integer.class) {
                int value = (int) property;
                preparedStatement.setInt(parameterIndex, value);

            } else {
                throw new WrongPreparedStatementType();
            }
        }

        // Returning prepared statement
        return preparedStatement;
    }


}
