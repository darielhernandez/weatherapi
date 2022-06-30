package com.careerdevs.weatherapi.models;

import com.careerdevs.weatherapi.models.CurrentWeather.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Forecast {

    private City city;

    private ForecastWeatherData[] list;

    public static class City{
        private String name;
        private Coords coord;
        private String country;
        private int population;

        public String getName() {
            return name;
        }

        public Coords getCoord() {
            return coord;
        }

        public String getCountry() {
            return country;
        }

        public int getPopulation() {
            return population;
        }
    }

    public static class ForecastWeatherData extends CurrentWeather{

        @JsonProperty("dt_txt")
        private String dateTime;

        private Float pop;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Coords coord;

        public Float getPop() {
            return pop;
        }

        public String getDateTime() {
            return dateTime;
        }

    }

    public City getCity() {
        return city;
    }

    public ForecastWeatherData[] getList() {
        return list;
    }

    public ForecastReport createReport (String units){
        return new ForecastReport(this, units);
    }
}
