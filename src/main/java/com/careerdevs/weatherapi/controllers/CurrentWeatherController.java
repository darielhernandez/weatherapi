package com.careerdevs.weatherapi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current")
public class CurrentWeatherController {


    @Autowired
    private Environment env;

    @GetMapping("/test")
    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
    public ResponseEntity<?> testRequest(){

        String apiKey = env.getProperty("OW_API_KEY");
        return ResponseEntity.ok("api key: " + apiKey);
    }
}
