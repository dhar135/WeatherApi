package io.github.dhar135.WeatherApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HourlyWeather(String datetime, Long datetimeEpoch, double temp, double feelsLike, double dew,
                            double humidity, double precip, double precipprob, List<String> preciptype,
                            double windSpeed, double winddir, double windGust, double uvIndex, String icon,
                            String condition) implements Serializable {
}