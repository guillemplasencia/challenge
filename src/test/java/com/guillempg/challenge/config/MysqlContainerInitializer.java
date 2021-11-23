package com.guillempg.challenge.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MysqlContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    private final MySQLContainer<?> mySQLContainer;

    public MysqlContainerInitializer()
    {
        mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
            //.withLogConsumer(new Slf4jLogConsumer(log))
            .withUsername("testuser")
            .withPassword("testpassword")
            .withEnv("MYSQL_ROOT_PASSWORD","test")
            .withFileSystemBind("src/test/resources/ddl","/docker-entrypoint-initdb.d")
            .withExposedPorts(3306)
            .withDatabaseName("challengedb");
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext)
    {
        mySQLContainer.start();
        TestPropertyValues.of(
            "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
            "spring.datasource.username=" + mySQLContainer.getUsername(),
            "spring.datasource.password=" + mySQLContainer.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}
