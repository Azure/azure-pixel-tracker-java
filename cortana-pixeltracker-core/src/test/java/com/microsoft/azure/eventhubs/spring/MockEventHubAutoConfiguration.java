package com.microsoft.azure.eventhubs.spring;

import com.microsoft.azure.eventhubs.IEventHubClient;

public class MockEventHubAutoConfiguration implements IEventHubAutoConfiguration {
    private MockEventHubClient mockEventHubClient;

    public MockEventHubAutoConfiguration() {
        this.mockEventHubClient = new MockEventHubClient();
    }

    @Override
    public IEventHubClient eventHubClient() {
        return this.mockEventHubClient;
    }
}