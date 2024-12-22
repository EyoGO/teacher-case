package com.eyogo.http.integration;

import com.eyogo.http.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql(value = {
        "classpath:sql/data.sql"
})
public abstract class IntegrationTestBase {
    // Inside the class there is default user/password, so we can specify that data in test properties, but port is
    // dynamic. To fix this we have @DynamicPropertySource:
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.4");

    @BeforeAll
    static void runContainer() {
        postgreSQLContainer.start();
    }

    // There is example in JAVADOC
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }
}
