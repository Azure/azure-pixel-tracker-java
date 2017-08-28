package com.microsoft.azure.server.pixeltracker.api.handlers.impl;

import com.microsoft.azure.server.pixeltracker.api.model.RequestImpl;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonQueryStringHandlerTest {
    private JsonQueryStringHandler jsonQueryStringHandler;

    @Before
    public void setUp() throws Exception {
        jsonQueryStringHandler = new JsonQueryStringHandler();
    }

    @Test
    public void strategyFail() throws Exception{
        Map<String, String> map = new HashMap<>();
        RequestImpl request = new RequestImpl(map);
        jsonQueryStringHandler.strategy(request);

        assertFalse(request.isSuccess());
    }

    @Test
    public void strategyPass() throws Exception {
        Map<String, String> map = new HashMap<>();
        RequestImpl request = new RequestImpl(map);

        map.put("this", "that");
        map.put("test","good");
        map.put("empty","");
        jsonQueryStringHandler.strategy(request);
        JSONObject json = request.getJson();
        assertTrue(json.keySet().size() == 2);
        assertTrue(json.has("this"));
        assertTrue(json.getString("this").equals("that"));
        assertTrue(json.has("test"));
        assertTrue(json.getString("test").equals("good"));
        assertFalse(json.has("empty"));
        assertTrue(request.isSuccess());
    }

}