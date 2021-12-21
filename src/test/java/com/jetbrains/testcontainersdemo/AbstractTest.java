package com.jetbrains.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class AbstractTest {
    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:8.0.26")
            .withDatabaseName("somedatabase") //we can omit this as well
            .withReuse(true);

    @BeforeAll
    public static void setup() {
        container.start();
    }

    @DynamicPropertySource
    public static void overrideProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
