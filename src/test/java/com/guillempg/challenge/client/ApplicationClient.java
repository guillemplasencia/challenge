package com.guillempg.challenge.client;

import org.springframework.test.web.reactive.server.WebTestClient;

public interface ApplicationClient
{
    WebTestClient getWebTestClient();
}
