package com.microsoft.azure.pixeltracker.server;

import com.microsoft.azure.server.pixeltracker.api.handlers.impl.JsonQueryStringHandler;
import com.microsoft.azure.server.pixeltracker.api.handlers.impl.PixelHandler;
import com.microsoft.azure.server.pixeltracker.api.model.RequestImpl;
import com.microsoft.azure.servicebus.ServiceBusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class PixelController {
    private final PixelHandler pixelHandler;
    private JsonQueryStringHandler jsonQueryStringHandler;

    public PixelController(PixelHandler pixelHandler) throws IOException, ServiceBusException {
        this.pixelHandler = pixelHandler;
    }

    @RequestMapping(value = "/pixel",
            produces = MediaType.IMAGE_GIF_VALUE,
            method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<Object> pixel(@RequestParam Map<String, String> queryParameters) {
        RequestImpl request = new RequestImpl(queryParameters);
        try {
            pixelHandler.handle(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (queryParameters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
