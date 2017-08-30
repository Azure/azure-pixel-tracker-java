package com.microsoft.azure.pixeltracker.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static com.microsoft.azure.pixeltracker.server.IndexControllerIT.getHostname;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PixelControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL(getHostname(port) + "pixel");
    }

    @Test
    public void getPixel() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "?this=that",
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getPixelEmptyArg() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "?this=",
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getPixelEmpty() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}