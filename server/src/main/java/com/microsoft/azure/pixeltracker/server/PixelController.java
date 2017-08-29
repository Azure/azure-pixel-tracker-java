package com.microsoft.azure.pixeltracker.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PixelController {

    @RequestMapping(value = "/pixel",
            produces = MediaType.IMAGE_GIF_VALUE,
            method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<Object> pixel(@RequestParam Map<String, String> queryParameters) {
        if (queryParameters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
