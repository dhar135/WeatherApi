package io.github.dhar135.WeatherApi.controller;

import io.github.dhar135.WeatherApi.model.WeatherResponse;
import io.github.dhar135.WeatherApi.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecast")
    public ResponseEntity<WeatherResponse> getWeatherForecast(
            @RequestParam String location,
            @RequestParam String date1,
            @RequestParam String date2
    ) {
        try {
            WeatherResponse weather = weatherService.getForecast(location, date1, date2);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        } catch (Exception e) {
            // In a real application, you'd handle specific exceptions
            // and map them to appropriate HTTP status codes (e.g., 404, 500)
            System.err.println("Error in controller: " + e.getMessage()); // Log the error
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 for errors
        }

    }


}
