package com.waes.diff.repositories;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

abstract class BaseRepository {

    private QueryRunner queryRunner;

    BaseRepository(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    long insert(String query, ResultSetHandler rsh, Object... params) throws SQLException {
        Connection conn = queryRunner.getDataSource().getConnection();
        queryRunner.insert(conn, query, rsh, params);
        long id = getLastInsertedId(conn);
        DbUtils.close(conn);
        return id;
    }

    <T> T select(String query, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        return queryRunner.query(query, rsh, params);
    }

    int update(String query, Object... params) throws SQLException {
        Connection conn = queryRunner.getDataSource().getConnection();
        int rows = queryRunner.update(conn, query, params);
        DbUtils.close(conn);
        return rows;
    }

    private long getLastInsertedId(Connection connection) throws SQLException {
        String query = "SELECT LAST_INSERT_ID() as val";
        final AtomicReference<Long> id = new AtomicReference<>();
        queryRunner.query(connection, query, (ResultSet rs) -> {
            if (rs.first()) {
                id.set(rs.getLong("val"));
            }
            return id;
        });
        return id.get();
    }


}
