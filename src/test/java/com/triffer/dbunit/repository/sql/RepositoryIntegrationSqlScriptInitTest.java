/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.repository.sql;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = RepositoryIntegrationSqlScriptInitTest.PostgresInitializer.class)
@Testcontainers
public abstract class RepositoryIntegrationSqlScriptInitTest {

    public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12");

    static {
        postgreDBContainer.start();
    }

    public static class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreDBContainer.getUsername(),
                    "spring.datasource.password=" + postgreDBContainer.getPassword()
            );
        }
    }
}
