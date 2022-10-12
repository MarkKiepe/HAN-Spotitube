package nl.han.dea.markkiepe.spotitube.datasource.util;

import nl.han.dea.markkiepe.spotitube.datasource.exceptions.SqlScriptDoesntExistException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible for getting the correct SQL scripts.
 * @see PropertyManager
 * @author Mark Kiepe
 * @since 1.0
 */
public class SqlProperties extends PropertyManager {

    private static final String SQLSCRIPT_PROPERTIES_FILENAME = "sqlscripts.properties";

    /**
     * Constructor that creates the SQL Properties class.
     * @since 1.0
     */
    @Inject
    public SqlProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass()
                    .getClassLoader()
                    .getResourceAsStream(SQLSCRIPT_PROPERTIES_FILENAME)
            );
        } catch(IOException e) {
            e.printStackTrace();
        }
        super.setPropertyFile(properties);
    }

    /**
     * Gets the requested SQL Script in {@link String} format from the given name.
     * @param scriptName Name of the script you are requesting in {@link String} format.
     * @throws {@link SqlScriptDoesntExistException} if the SQL Script could not be found.
     * @return Requested SQL Script in {@link String} format.
     * @since 1.0
     */
    public String getSqlScript(String scriptName) {
        String script = properties.getProperty(scriptName);
        if (script != null) {
            return script;
        }
        throw new SqlScriptDoesntExistException();
    }

}
