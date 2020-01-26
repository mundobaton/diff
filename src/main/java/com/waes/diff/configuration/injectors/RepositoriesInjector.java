package com.waes.diff.configuration.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.waes.diff.core.repositories.FindContentRepository;
import com.waes.diff.core.repositories.SaveContentRepository;
import com.waes.diff.core.repositories.UpdateContentRepository;
import com.waes.diff.repositories.DefaultContentRepository;
import org.apache.commons.dbutils.QueryRunner;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Register repositories
 */
public class RepositoriesInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(FindContentRepository.class).to(DefaultContentRepository.class);
        bind(SaveContentRepository.class).to(DefaultContentRepository.class);
        bind(UpdateContentRepository.class).to(DefaultContentRepository.class);
    }

    /**
     * Injects a datasource (connection pool) and returns an utility object for querying the database.
     * @param dataSource
     * @return
     */
    @Provides
    @Singleton
    @Inject
    QueryRunner provideQueryRunner(final DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
