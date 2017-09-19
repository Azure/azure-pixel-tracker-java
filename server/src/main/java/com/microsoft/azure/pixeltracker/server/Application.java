package com.microsoft.azure.pixeltracker.server;

import com.microsoft.azure.eventhubs.spring.EventHubAutoConfiguration;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.EventHubSendHandler;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.JsonQueryStringHandler;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.PersonalizedOffersHandlerImpl;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.PixelHandler;
import com.microsoft.azure.servicebus.ServiceBusException;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

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

        if (System.getenv().containsKey("PersonalizedOffers_ConnString")) {
            pixelHandler.setNextOperation(new PersonalizedOffersHandlerImpl(cloudQueue()));
        }

        return pixelHandler;
    }

    @Bean
    public EventHubAutoConfiguration eventHubAutoConfiguration() throws IOException, ServiceBusException {
        return new EventHubAutoConfiguration();
    }

    public static CloudQueue cloudQueue() {
        String storageConnectionString = System.getenv("PersonalizedOffers_ConnString");
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            return storageAccount.createCloudQueueClient().getQueueReference("useractionqueue");
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            e.printStackTrace();
        }
        return null;
    }
}