/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.repository.dbunit;

import com.triffer.dbunit.testsupport.RepositoryTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public abstract class RepositoryIntegrationDbunitInitTest extends RepositoryTest {

    public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12");

    static {
        postgreDBContainer.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreDBContainer::getUsername);
        registry.add("spring.datasource.password", postgreDBContainer::getPassword);
    }

}
