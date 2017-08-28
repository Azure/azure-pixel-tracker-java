package com.microsoft.azure.server.pixeltracker.api.handlers.impl;

import com.microsoft.azure.eventhubs.spring.MockEventHubAutoConfiguration;
import com.microsoft.azure.server.pixeltracker.api.model.RequestImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventHubSendHandlerTest {

    private MockEventHubAutoConfiguration mockEventHubAutoConfiguration;
    private EventHubSendHandler eventHubSendHandler;
    private JsonQueryStringHandler jsonQueryStringHandler;

    @Before
    public void setUp() throws Exception {
        mockEventHubAutoConfiguration = new MockEventHubAutoConfiguration();
        jsonQueryStringHandler = new JsonQueryStringHandler();
        eventHubSendHandler = new EventHubSendHandler(mockEventHubAutoConfiguration);
    }

    @Test
    public void strategyPass() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("this", "that");
        map.put("test","good");
        map.put("empty","");
        RequestImpl request = new RequestImpl(map);

        jsonQueryStringHandler.strategy(request);
        eventHubSendHandler.strategy(request);

        assertTrue(request.isSuccess());
    }

    @Test
    public void strategyFail() throws Exception{
        RequestImpl request = new RequestImpl(new HashMap<>());
        jsonQueryStringHandler.strategy(request);
        eventHubSendHandler.strategy(request);

        assertFalse(request.isSuccess());
    }

}