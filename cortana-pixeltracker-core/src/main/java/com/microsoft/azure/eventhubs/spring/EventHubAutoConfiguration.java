/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package com.microsoft.azure.eventhubs.spring;

import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.IEventHubClient;
import com.microsoft.azure.servicebus.ServiceBusException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EventHubAutoConfiguration implements IEventHubAutoConfiguration {
    private static Logger logger = LogManager.getLogger();
    private IEventHubClient ehClient;

    public EventHubAutoConfiguration() throws IOException, ServiceBusException {
        if (System.getenv().containsKey("CUSTOMCONNSTR_ConnString1")) {
            String connStr = System.getenv("CUSTOMCONNSTR_ConnString1");
            ehClient = EventHubClient.createFromConnectionStringSync(connStr.toString());
            logger.info("Event Hub env var found!");
        } else {
            ehClient = new MockEventHubClient();
            logger.warn("Event Hub env var missing! Testing?");
        }
    }

    public EventHubAutoConfiguration(IEventHubClient ehClient) {
        this.ehClient = ehClient;
    }

    @Override
    public IEventHubClient eventHubClient() {
        return ehClient;
    }
}
