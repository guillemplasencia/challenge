package com.guillempg.challenge;

import com.guillempg.challenge.config.MysqlContainerInitializer;
import com.guillempg.challenge.config.TestConfig;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class, TestConfig.class}, initializers = {MysqlContainerInitializer.class})
public class CucumberTestConfig
{
}
