package com.waes.diff.configuration.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Registers database connection pool
 */
public class DatabaseInjector extends AbstractModule {

    @Override
    protected void configure() {

    }

    /**
     * Registers a new connection pool available for further usage
     *
     * @return the connection pool
     */
    @Provides
    @Singleton
    @Inject
    protected DataSource provideDataSource(Properties databaseProperties) {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(databaseProperties.getProperty("com.waes.db.config.driver"));
        config.setJdbcUrl(databaseProperties.getProperty("com.waes.db.config.jdbc.url"));
        config.setUsername(databaseProperties.getProperty("com.waes.db.config.username"));
        config.setPassword(databaseProperties.getProperty("com.waes.db.config.password"));
        config.addDataSourceProperty("cachePrepStmts", Boolean.TRUE);
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", Boolean.TRUE);
        config.addDataSourceProperty("useLocalSessionState", Boolean.TRUE);
        config.addDataSourceProperty("useLocalTransactionState", Boolean.TRUE);
        config.addDataSourceProperty("rewriteBatchedStatements", Boolean.TRUE);
        config.addDataSourceProperty("cacheResultSetMetadata", Boolean.TRUE);
        config.addDataSourceProperty("cacheServerConfiguration", Boolean.TRUE);
        config.addDataSourceProperty("elideSetAutoCommits", Boolean.TRUE);
        config.addDataSourceProperty("maintainTimeStats", Boolean.FALSE);
        config.addDataSourceProperty("serverTimezone", "GMT-3");
        config.setAutoCommit(true);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(Long.parseLong(databaseProperties.getProperty("com.waes.db.config.conn.timeout")));
        config.setIdleTimeout(Long.parseLong(databaseProperties.getProperty("com.waes.db.config.idle.timeout")));

        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("useUnicode", "true");

        DataSource ds = new HikariDataSource(config);

        return ds;
    }
}
