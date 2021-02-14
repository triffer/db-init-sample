/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.testsupport.config;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DbUnitInitializer {

    public static TestDatabase getDbUnitDatabase(DataSource dataSource) {
        try {
            IDatabaseConnection conn = new DatabaseDataSourceConnection(dataSource);
            DatabaseConfig config = conn.getConfig();
            config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            config.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
            config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
            return new TestDatabase(conn);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create test database.", e);
        }
    }
}
