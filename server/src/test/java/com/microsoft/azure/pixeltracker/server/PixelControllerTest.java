package com.microsoft.azure.pixeltracker.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PixelControllerTest {

    private String urlTemplate = "/pixel";

    @Autowired
    private MockMvc mvc;

    @Test
    public void getPixel() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(urlTemplate + "?this=that")
                .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isOk());
    }

    @Test
    public void getPixelEmptyArg() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(urlTemplate + "?this=")
                .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isOk());
    }


    @Test
    public void getPixelEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPixel() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isMethodNotAllowed());
    }
}