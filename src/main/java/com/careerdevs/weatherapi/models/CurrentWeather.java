package com.careerdevs.weatherapi.models;


import java.util.Arrays;

public class CurrentWeather {

    private String name;

    private int timezone;

    private int visibility;

    private Coords coord;

    private Main main;

    private Weather[] weather;

    public String getName() {
        return name;
    }

    public int getTimezone() {
        return timezone;
    }

    public int getVisibility() {
        return visibility;
    }

    public Coords getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public static class Coords {
        private float lon;
        private float lat;

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }
    }

    public static class Main{
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private int pressure;
        private float humidity;

        public float getTemp() {
            return temp;
        }

        public float getFeels_like() {
            return feels_like;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public int getPressure() {
            return pressure;
        }

        public float getHumidity() {
            return humidity;
        }
    }

    public static class Weather{

        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Weather{");
            sb.append("\"id\":").append(id);
            sb.append(", \"main\":\"").append(main).append('"');
            sb.append(", \"description\":\"").append(description).append('"');
            sb.append(", \"icon\":\"").append(icon).append('"');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CurrentWeather{");
        sb.append("\"name\":\"").append(name).append('"');
        sb.append(", \"timezone\":").append(timezone);
        sb.append(", \"visibility\":").append(visibility);
        sb.append(", \"coord\":").append(coord);
        sb.append(", \"main\":").append(main);
        sb.append(", \"weather\":").append(weather == null ? "null" : Arrays.asList(weather).toString());
        sb.append('}');
        return sb.toString();
    }

    public CurrentWeatherReport createReport(String units) {
        return new CurrentWeatherReport(
                getName(),
                getCoord(),
                getMain(),
                getWeather()[0],
                units
        );
    }
}
