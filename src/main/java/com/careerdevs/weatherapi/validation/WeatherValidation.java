package com.careerdevs.weatherapi.validation;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherValidation {

    public static HashMap<String , String> validateQuery(String city, String units){

        HashMap<String, String> validationErrors = new HashMap<>();

//            Validation- name
//            name cant be blank
        if(city.trim().equals("")){
            validationErrors.put("city", "City name required");

        } else if (!city.replaceAll("[^a-zA-z -]", "").equals(city)
        ){
//            name should not include special char/num
            validationErrors.put("city", "Invalid city name");
        }

//            validation- units
//            is it metric or imperial
        if(!units.equals("metric") && !units.equals("imperial")) {
            validationErrors.put("units", "units must be in metric or imperial");
        }

        return validationErrors;

    }
}
