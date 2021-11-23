package com.guillempg.challenge.client;

import org.springframework.test.web.reactive.server.WebTestClient;

public class ApplicationClientImpl implements ApplicationClient
{
    private final WebTestClient webTestClient;

    public ApplicationClientImpl(final WebTestClient webTestClient)
    {
        this.webTestClient = webTestClient;
    }

    @Override
    public WebTestClient getWebTestClient()
    {
        return this.webTestClient;
    }
}
