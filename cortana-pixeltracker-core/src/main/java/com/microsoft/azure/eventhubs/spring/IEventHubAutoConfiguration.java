package com.microsoft.azure.eventhubs.spring;

import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.IEventHubClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public interface IEventHubAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean({EventHubClient.class})
    IEventHubClient eventHubClient();
}
