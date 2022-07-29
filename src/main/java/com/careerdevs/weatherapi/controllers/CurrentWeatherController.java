package com.careerdevs.weatherapi.controllers;


import com.careerdevs.weatherapi.models.CurrentWeather;
import com.careerdevs.weatherapi.models.CurrentWeatherReport;
import com.careerdevs.weatherapi.repositories.CurrentReportRepository;
import com.careerdevs.weatherapi.validation.WeatherValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/api/current")
public class CurrentWeatherController {

    @Autowired
    private Environment env;

    private final String BASE_URL= "https://api.openweathermap.org/data/2.5/weather";

    @Autowired
    private CurrentReportRepository currentReportRepo;

    @GetMapping("/city/{cityName}")
    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
    public ResponseEntity<?> getCurrentWeatherByCityPV (RestTemplate restTemplate, @PathVariable String cityName){

        try {

            String units = "imperial";
            HashMap<String, String> validationErrors = WeatherValidation.validateQuery(cityName, units);

            //if validation fails in any way, return error message(s)
            if(validationErrors.size() !=0){
                return ResponseEntity.badRequest().body(validationErrors);
            }
            String apiKey = env.getProperty("OW_API_KEY");
            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial";
            String openWeatherURL = BASE_URL + queryString;

            CurrentWeather owRes = restTemplate.getForObject(openWeatherURL, CurrentWeather.class);

            assert owRes != null;

            return ResponseEntity.ok(owRes.createReport(units));

        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }


    //http:localhost:8080/api/current/current?name=boston&units=imperial

    @GetMapping("/city")
    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
    public ResponseEntity<?> getCurrentWeatherByCityRP (
            RestTemplate restTemplate,
            @RequestParam(value= "name") String cityName,
            @RequestParam(defaultValue = "imperial") String units)
    {

        try {
            HashMap<String, String> validationErrors = WeatherValidation.validateQuery(cityName, units);

              //if validation fails in any way, return error message(s)
              if(validationErrors.size() !=0){
                  return ResponseEntity.badRequest().body(validationErrors);
              }

            String apiKey = env.getProperty("OW_API_KEY");
            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial" + units;
            String openWeatherURL = BASE_URL + queryString;

            CurrentWeather owRes = restTemplate.getForObject(openWeatherURL, CurrentWeather.class);
            assert owRes!=null;

            //upload to the database
//            CurrentWeatherReport saveReport = currentReportRepo.save(owRes.createReport(units));

            return ResponseEntity.ok(owRes.createReport(units));

        }catch (HttpClientErrorException.NotFound e){// cityName validation
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/city")
    public ResponseEntity<?> uploadCurrentWeatherByCityRP (
            RestTemplate restTemplate,
            @RequestParam(value= "name") String cityName,
            @RequestParam(defaultValue = "imperial") String units)
    {

        try {
            HashMap<String, String> validationErrors = WeatherValidation.validateQuery(cityName, units);

            //if validation fails in any way, return error message(s)
            if(validationErrors.size() !=0){
                return ResponseEntity.badRequest().body(validationErrors);
            }

            String apiKey = env.getProperty("OW_API_KEY");
            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial" + units;
            String openWeatherURL = BASE_URL + queryString;

            CurrentWeather owRes = restTemplate.getForObject(openWeatherURL, CurrentWeather.class);
            assert owRes!=null;
            return ResponseEntity.ok(owRes.createReport(units));

        }catch (HttpClientErrorException.NotFound e){// cityName validation
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
