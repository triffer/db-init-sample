/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.repository.sql;

import com.triffer.dbunit.testsupport.RepositoryContainerTest;
import com.triffer.dbunit.testsupport.config.DbUnitInitializer;
import com.triffer.dbunit.testsupport.config.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

public abstract class RepositorySqlScriptInitTest extends RepositoryContainerTest {

    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/testdata/person/clean.sql")
    @BeforeEach
    public void setupTest() {
    }
}
