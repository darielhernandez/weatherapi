package com.careerdevs.weatherapi.controllers;


import com.careerdevs.weatherapi.models.CurrentWeather;
import com.careerdevs.weatherapi.models.CurrentWeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/current")
public class CurrentWeatherController {

    @Autowired
    private Environment env;

    private final String BASE_URL= "https://api.openweathermap.org/data/2.5/weather";

    @GetMapping("/city/{cityName}/{units}")
    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
    public ResponseEntity<?> getCurrentWeatherByCityPV (RestTemplate restTemplate, @PathVariable String cityName,@PathVariable String units){

        try {
            String apiKey = env.getProperty("OW_API_KEY");
            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial";
            String openWeatherURL = BASE_URL + queryString;

            CurrentWeather owRes = restTemplate.getForObject(openWeatherURL, CurrentWeather.class);

            assert owRes != null;

            CurrentWeatherReport report = new CurrentWeatherReport(
                    owRes.getName(),
                    owRes.getCoord(),
                    owRes.getMain(),
                    owRes.getWeather()[0],
                    units

            );

            System.out.println(report);


            return ResponseEntity.ok(report);

        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }


    //http://localhost:8080/api/current/current?name=boston&units=imperial

    @GetMapping("/city")
    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
    public ResponseEntity<?> getCurrentWeatherByCityRP (
            RestTemplate restTemplate,
            @RequestParam String name,
            @RequestParam(value= "name") String cityName,
            @RequestParam(defaultValue = "imperial") String units){

        try {
            ArrayList<String> validationErrors = new ArrayList<>();
//            Validation- name
//            name cant be blank
            if(cityName.trim().equals("")){
                validationErrors.add("City name required");

            } else if (!cityName.replaceAll("[^a-zA-z -]", "").equals(cityName)
            ){
//            name should not include special char/num
            validationErrors.add("Invalid city name");
            }

//            validation- units
//            is it metric or imperial
              if(!units.equals("metric") && !units.equals("imperial")) {
                  validationErrors.add("units must be in metric or imperial");
              }

              if(validationErrors.size() !=0){
                  return ResponseEntity.badRequest().body(validationErrors);
              }

            String apiKey = env.getProperty("OW_API_KEY");
            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial" + units;
            String openWeatherURL = BASE_URL + queryString;

            CurrentWeather owRes = restTemplate.getForObject(openWeatherURL, CurrentWeather.class);

            return ResponseEntity.ok().body(owRes);

        }catch (HttpClientErrorException.NotFound e){// cityName validation
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
