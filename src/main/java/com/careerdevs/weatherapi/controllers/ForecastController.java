package com.careerdevs.weatherapi.controllers;

import com.careerdevs.weatherapi.models.Forecast;
import com.careerdevs.weatherapi.models.ForecastReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    @Autowired
    private Environment env;
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    @GetMapping("/city/{cityName}")
    public ResponseEntity<?> getForecastByCityPV (RestTemplate restTemplate, @PathVariable String cityName){

        try{
            String units= "imperial";
            String apiKey= env.getProperty("OW_API_KEY");
            String queryString= "?q=" + cityName + "&units=" + units + "&appid=" +  apiKey;
            String url = BASE_URL +queryString;
            Forecast owRes = restTemplate.getForObject(url, Forecast.class);


            //generate report
            assert owRes != null;

//            return ResponseEntity.ok(report);
            return ResponseEntity.ok(owRes.createReport(units));

            }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

            }catch (Exception e){
            System.out.println(e);
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity<?> getForecastByCityReqParams (RestTemplate restTemplate, @PathVariable String cityName){

        try{
            String units= "imperial";
            String apiKey= env.getProperty("OW_API_KEY");
            String queryString= "?q=" + cityName + "&units=" + units + "&appid=" +  apiKey;
            String url = BASE_URL +queryString;
            Forecast owRes = restTemplate.getForObject(url, Forecast.class);


            //generate report
            assert owRes != null;

//            return ResponseEntity.ok(report);
            return ResponseEntity.ok(owRes.createReport(units));

        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("City Not Found: " + cityName);

        }catch (Exception e){
            System.out.println(e);
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
