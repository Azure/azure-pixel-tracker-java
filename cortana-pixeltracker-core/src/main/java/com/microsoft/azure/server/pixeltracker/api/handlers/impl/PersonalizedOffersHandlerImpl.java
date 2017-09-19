package com.microsoft.azure.server.pixeltracker.api.handlers.impl;

import com.microsoft.azure.server.pixeltracker.api.model.Request;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

public class PersonalizedOffersHandlerImpl extends AbstractHandler {
    private CloudQueue queue;

    public PersonalizedOffersHandlerImpl(CloudQueue queue) {
        this.queue = queue;
    }

    @Override
    public void strategy(Request request) throws Exception {
        if (request.isSuccess()) {
            queue.addMessage(new CloudQueueMessage(request.getJson().toString()));
            request.setSuccess(true);
        } else {
            request.setSuccess(false);
        }
    }
}
