package nl.han.dea.markkiepe.spotitube.datasource.util;

import java.util.Properties;

/**
 * Property Manager that manages the property file.
 * @author Mark Kiepe
 * @since 1.0
 */
public class PropertyManager {

    protected Properties properties;

    /**
     * Sets the property file to a new file.
     * @param properties {@link Properties}
     * @since 1.0
     */
    protected void setPropertyFile(Properties properties) {
        this.properties = properties;
    }

    /**
     * Gets the current property file that is being used.
     * @return {@link Properties}
     * @since 1.0
     */
    public Properties getPropertyFile() {
        return properties;
    }

}
