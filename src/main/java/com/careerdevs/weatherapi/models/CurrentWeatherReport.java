package com.careerdevs.weatherapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CurrentWeatherReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long id;

    private String name;
    private String main;
    private String description;
    private String units;
    private float temp;
    private float feelsLike;
    private float tempMin;
    private float tempMax;
    private float pressure;
    private float humidity;
    private float lat;
    private float lon;

    public CurrentWeatherReport(){

    }

    public CurrentWeatherReport(
            String name,
            CurrentWeather.Coords coords,
            CurrentWeather.Main main,
            CurrentWeather.Weather weather,
            String units
    ) {
        this.units = units;
        this.name = name;
        lon = coords.getLon();
        lat = coords.getLat();
        temp = main.getTemp();
        feelsLike = main.getFeels_like();
        tempMin = main.getTemp_min();
        tempMax = main.getTemp_max();
        pressure = main.getPressure();
        humidity = main.getHumidity();
        this.main = weather.getMain();
        description = weather.getDescription();

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

    public float getTemp() {
        return temp;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getUnits() {
        return units;
    }

//    @Override
//    public String toString() {
//        return "CurrentWeatherReport{" +
//                "units='" + units + '\'' +
//                ", name='" + name + '\'' +
//                ", lon=" + lon +
//                ", lat=" + lat +
//                ", temp=" + temp +
//                ", feelsLike=" + feelsLike +
//                ", tempMin=" + tempMin +
//                ", tempMax=" + tempMax +
//                ", pressure=" + pressure +
//                ", humidity=" + humidity +
//                ", main='" + main + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CurrentWeatherReport{");
        sb.append("\"name\":\"").append(name).append('"');
        sb.append(", \"main\":\"").append(main).append('"');
        sb.append(", \"description\":\"").append(description).append('"');
        sb.append(", \"units\":\"").append(units).append('"');
        sb.append(", \"temp\":").append(temp).append(units.equals("imperial") ? "F" : "C").append("°\"");
        sb.append(", \"feelsLike\":").append(feelsLike).append(units.equals("imperial") ? "F" : "C").append("°\"");
        sb.append(", \"tempMin\":").append(tempMin).append(units.equals("imperial") ? "F" : "C").append("°\"");
        sb.append(", \"tempMax\":").append(tempMax).append(units.equals("imperial") ? "F" : "C").append("°\"");
        sb.append(", \"pressure\":").append(pressure);
        sb.append(", \"humidity\":").append(humidity);
        sb.append(", \"lat\":").append(lat);
        sb.append(", \"lon\":").append(lon);
        sb.append('}');
        return sb.toString();
    }
}

