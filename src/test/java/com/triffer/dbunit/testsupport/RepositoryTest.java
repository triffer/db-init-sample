/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.testsupport;

import com.triffer.dbunit.testsupport.config.DbUnitInitializer;
import com.triffer.dbunit.testsupport.config.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public abstract class RepositoryTest {

    protected static TestDatabase testDatabase;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setupTest() {
        if (testDatabase == null) {
            testDatabase = DbUnitInitializer.getDbUnitDatabase(dataSource);
        }

        testDatabase.truncateAllTables();
    }
}
