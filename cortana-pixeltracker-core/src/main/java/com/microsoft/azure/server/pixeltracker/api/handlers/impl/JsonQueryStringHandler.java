package com.microsoft.azure.server.pixeltracker.api.handlers.impl;

import com.microsoft.azure.server.pixeltracker.api.model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class JsonQueryStringHandler extends AbstractHandler {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void strategy(Request request) throws Exception {
        logger.trace("Json Query String Strategy");
        Map<String, String> queryString = request.getQueryString();
        if (isNotEmpty(queryString)) queryString.forEach((key, value) -> {
            if (value != "") {
                request.put(key, value);
                request.setSuccess(true);
            }
        });
        else request.setSuccess(false);
    }

    private boolean isNotEmpty(Map<String, String> queryString) {
        return queryString.size() > 0;
    }
}
