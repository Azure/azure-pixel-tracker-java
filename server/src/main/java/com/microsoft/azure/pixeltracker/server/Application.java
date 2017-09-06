package com.microsoft.azure.pixeltracker.server;

import com.microsoft.azure.eventhubs.spring.EventHubAutoConfiguration;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.EventHubSendHandler;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.JsonQueryStringHandler;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.PixelHandler;
import com.microsoft.azure.servicebus.ServiceBusException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PixelHandler pixelHandler(EventHubAutoConfiguration eventHubAutoConfiguration) {
        PixelHandler pixelHandler = new PixelHandler();

        pixelHandler.setNextOperation(new JsonQueryStringHandler());
        pixelHandler.setNextOperation(new EventHubSendHandler(eventHubAutoConfiguration));

        return pixelHandler;
    }

    @Bean
    public EventHubAutoConfiguration eventHubAutoConfiguration() throws IOException, ServiceBusException {
        return new EventHubAutoConfiguration();
    }
}