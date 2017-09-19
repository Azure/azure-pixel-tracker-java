package com.microsoft.azure.pixeltracker.server;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    @RequestMapping("/")
    public String index() {
        JSONObject jsonObject = new JSONObject();
        if (System.getenv().containsKey("CUSTOMCONNSTR_ConnString1")) {
            jsonObject.put("EventHub", true);
        }
        if (System.getenv().containsKey("PersonalizedOffers_ConnString")) {
            jsonObject.put("Personalized Offers", true);
        }
        if (System.getenv().containsKey("Customer360_ConnString")) {
            jsonObject.put("Customer 360", true);
        }
        return jsonObject.toString(4);
    }

}