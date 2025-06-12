package io.github.dhar135.WeatherApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DailyWeather(String datetime, Long datetimeEpoch, double tempMax, double tempMin, double temp,
                           double feelsLike, double feelsLikeMax, double feelsLikeMin, double dew, double humidity,
                           double precip, double precipprob, List<String> preciptype, double windSpeed, double winddir,
                           double windGust, double uvIndex, String description, String icon, String sunrise,
                           String sunset, String condition, List<HourlyWeather> hours) implements Serializable {

}
