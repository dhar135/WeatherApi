package io.github.dhar135.WeatherApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;


// Use @JsonIgnoreProperties to ignore any JSON fields not mapped to Java fields
// This prevents errors if the API sends fields you don't define in your Java classes.
@JsonIgnoreProperties(ignoreUnknown = true)
@RedisHash("WeatherResponse") // Added @RedisHash annotation
public record WeatherResponse(
        // Consider adding @Id to one of these fields if you want to control the Redis key directly
        // If no @Id is specified, Spring Data Redis will generate one.
        // For example, if 'resolvedAddress' and 'description' (or a combination) uniquely identify a forecast,
        // you could create a composite key or use one of them as @Id.
        // For now, we'll let Spring Data Redis generate IDs.
        @Id
        String id,
        double latitude,
        double longitude,
        String resolvedAddress,
        String address,
        String description,
        List<DailyWeather> days) implements Serializable {
}
