package com.careerdevs.weatherapi.controllers;


import com.careerdevs.weatherapi.models.CurrentWeather;
import com.careerdevs.weatherapi.models.CurrentWeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/current")
public class CurrentWeatherController {

    @Autowired
    private Environment env;

    private final String BASE_URL= "https://api.openweathermap.org/data/2.5/weather";

    @GetMapping("/city/{cityName}")
    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
    public ResponseEntity<?> getCurrentWeatherByCityPV (RestTemplate restTemplate, @PathVariable String cityName){

        try {
            String units= "imperial";
            String apiKey = env.getProperty("OW_API_KEY");
            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial";
            String openWeatherURL = BASE_URL + queryString;

            CurrentWeather owRes = restTemplate.getForObject(openWeatherURL, CurrentWeather.class);

            assert owRes != null;
//            System.out.println("city: " + openWeatherResponse.getName());
//            System.out.println("Temp: " + openWeatherResponse.getMain().getTemp());
//            System.out.println("Desc: " + openWeatherResponse.getWeather()[0].getDescription());
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

//    @GetMapping("/")
//    //response entity lets you have a more fine level of control over things like status, data, and headers in your responses
//    public ResponseEntity<?> getCurrentWeatherByCityRP (RestTemplate restTemplate, @RequestParam("city") String cityName){
//
//        try {
//            String apiKey = env.getProperty("OW_API_KEY");
//            String queryString = "?q=" + cityName + "&appid=" + apiKey + "&units=imperial";
//            String openWeatherURL = BASE_URL + queryString;
//
//            String openWeatherResponse = restTemplate.getForObject(openWeatherURL, String.class);
//
//            return ResponseEntity.ok(openWeatherResponse);
//
//        }catch (HttpClientErrorException.NotFound e){
//            return ResponseEntity.status(404).body("City not found: " + cityName);
//
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//            System.out.println(e.getClass());
//
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//
//    }

}
