package com.waes.diff.configuration.injectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.helpers.LogLog;

import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.InterruptedIOException;
import java.util.Properties;

/**
 * Registers some utilities
 */
@Slf4j
public class UtilsInjector extends AbstractModule {

    @Override
    protected void configure() {

    }

    /**
     * Register an object mapper (Jackson) used for data serialization/deserialization
     *
     * @return the object mapper
     */
    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }

    /**
     * Reads from the file database.properties and leaves an instance of Properties for further usage
     *
     * @return the properties instance
     */
    @Provides
    @Singleton
    public Properties getDatabaseProperties() {
        String filename = "database.properties";
        Properties props = new Properties();
        FileInputStream istream = null;
        try {
            File file = new File(UtilsInjector.class.getClassLoader().getResource(filename).getFile());
            istream = new FileInputStream(file);
            props.load(istream);
            istream.close();
        } catch (Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("Could not read configuration file [" + filename + "].", e);
            log.error("Ignoring configuration file [" + filename + "].");
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (InterruptedIOException ignore) {
                    Thread.currentThread().interrupt();
                } catch (Throwable ignore) {
                }
            }
        }
        return props;
    }
}
