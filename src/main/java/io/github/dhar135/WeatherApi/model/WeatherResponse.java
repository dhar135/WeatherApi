package io.github.dhar135.WeatherApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;


// Use @JsonIgnoreProperties to ignore any JSON fields not mapped to Java fields
// This prevents errors if the API sends fields you don't define in your Java classes.
@JsonIgnoreProperties(ignoreUnknown = true) //
public record WeatherResponse(
        double latitude,
        double longitude,
        String resolvedAddress,
        String address,
        String description,
        List<DailyWeather> days) implements Serializable {}
