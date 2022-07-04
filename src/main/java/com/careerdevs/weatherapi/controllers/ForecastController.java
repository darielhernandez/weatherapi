package com.careerdevs.weatherapi.controllers;

import com.careerdevs.weatherapi.models.Forecast;
import com.careerdevs.weatherapi.models.ForecastReport;
import com.careerdevs.weatherapi.validation.WeatherValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    @Autowired
    private Environment env;

    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    @GetMapping("/city/{city}")
    public ResponseEntity<?> getForecastByCityPV (RestTemplate restTemplate, @PathVariable String city){
        try{
            String units= "imperial";
            HashMap<String, String> validationErrors = WeatherValidation.validateQuery(city, units);
            //if validation fails in any way, return error message(s)
            if(validationErrors.size() !=0){
                return ResponseEntity.badRequest().body(validationErrors);
            }

            String apiKey= env.getProperty("OW_API_KEY");
            String queryString= "?q=" + city + "&units=" + units + "&appid=" +  apiKey;
            String url = BASE_URL +queryString;
            Forecast owRes = restTemplate.getForObject(url, Forecast.class);


            //generate report
            assert owRes != null;

//            return ResponseEntity.ok(report);
            return ResponseEntity.ok(owRes.createReport(units));

        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("City Not Found: " + city);

        }catch (Exception e){
            System.out.println(e);
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/city")
    public ResponseEntity<?> getForecastByCityReqParams (RestTemplate restTemplate, @RequestParam(value= "name") String city, @RequestParam(defaultValue = "imperial") String units){
        try{
            HashMap<String, String> validationErrors = WeatherValidation.validateQuery(city, units);

            //if validation fails in any way, return error message(s)
            if(validationErrors.size() !=0){
                return ResponseEntity.badRequest().body(validationErrors);
            }
            String apiKey= env.getProperty("OW_API_KEY");
            String queryString= "?q=" + city + "&units=" + units + "&appid=" +  apiKey;
            String url = BASE_URL +queryString;
            Forecast owRes = restTemplate.getForObject(url, Forecast.class);


            //generate report
            assert owRes != null;
            return ResponseEntity.ok(owRes.createReport(units));

        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("City Not Found: " + city);

        }catch (Exception e){
            System.out.println(e);
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
