package io.github.dhar135.WeatherApi.service;

import io.github.dhar135.WeatherApi.ThirdPartyWeatherApiClient.WeatherApiClient;
import io.github.dhar135.WeatherApi.model.WeatherResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    // Inject Repository and API Client
    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    // "weather" is the name of your cache.
    // "#location" is the key for the cache entry, derived from the method argument.
    // Unless specified, the default CacheManager will handle serialization to
    // Redis.
    // To set TTL for @Cacheable, you'd configure a RedisCacheManager bean (more
    // advanced).
    @Cacheable(value = "weather", key = "#location + '-' + #date1 + '-' + #date2")
    public WeatherResponse getForecast(String location, String date1, String date2) {
        System.out.println("Fetching weather from external API for: " + location); // This will only print on cache miss

        // Get the response from the API client
        WeatherResponse response = weatherApiClient.getForecast(location, date1, date2);

        // Create a new WeatherResponse with the generated ID
        return new WeatherResponse(
                generateId(location, date1, date2), // Generate a unique ID
                response.latitude(),
                response.longitude(),
                response.resolvedAddress(),
                response.address(),
                response.description(),
                response.days());
    }

    private String generateId(String location, String date1, String date2) {
        // Create a unique ID by combining location and dates
        return String.format("%s-%s-%s",
                location.replaceAll("[^a-zA-Z0-9]", "-"), // Sanitize location
                date1,
                date2 != null ? date2 : "single-day");
    }
}
