package com.microsoft.azure.pixeltracker.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerIT {

    /**
     * Can this be set manually using "local.server.port"?
     */
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
//        this.base = new URL(getHostname() + ":" + port + "/");
        this.base = new URL(getHostname(port) + "/");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        Properties properties = System.getProperties();
        if (!properties.containsKey("hostname"))
            assertThat(response.getBody(), equalTo("{}"));
    }

    public static String getHostname(int port) {
        String hostname = "http://localhost" + ":" + port + "/";
//        if (!System.getProperties().contains("hostname")) {
//            System.setProperty("hostname", "http://localhost"+ ":" + port + "/");
//            System.setProperty("hostname", "http://intstreamingestwebapp.azurewebsites2.net/");
//        }
        Properties properties = System.getProperties();
        if (properties.containsKey("hostname"))
            hostname = System.getProperty("hostname");
        System.out.println("hostname = " + hostname);
        return hostname;
    }
}