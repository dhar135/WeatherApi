package io.github.dhar135.WeatherApi.ThirdPartyWeatherApiClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dhar135.WeatherApi.model.WeatherResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Data
public class WeatherApiClient {

    @Value("${API_KEY}")
    private String apiKey;

    @Value("${API_BASE_URL}")
    private String baseUrl;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public WeatherApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public WeatherResponse getForecast(String location, String date1, String date2) {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Base url not set");
        }

        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("Api key not set");
        }

        if (location == null || location.isEmpty()) {
            throw new RuntimeException("Location not set");
        }

        if (date1 == null || date1.isEmpty()) {
            throw new RuntimeException("Start date not set");
        }

        try {
            // Format dates to yyyy-MM-dd format
            String formattedDate1 = LocalDate.parse(date1, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    .format(DATE_FORMATTER);
            String formattedDate2 = date2 != null ? LocalDate.parse(date2, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    .format(DATE_FORMATTER) : null;

            // URL encode the location
            String encodedLocation = java.net.URLEncoder.encode(location, java.nio.charset.StandardCharsets.UTF_8);

            // Construct the URL according to Visual Crossing API format
            String path = String.format("/%s/%s", encodedLocation, formattedDate1);
            if (formattedDate2 != null) {
                path += "/" + formattedDate2;
            }

            URI uri = URI.create(this.baseUrl + path + "?key=" + apiKey);
            System.out.println("Making request to: " + uri); // Debug log

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .join();

            // Check HTTP status code
            if (response.statusCode() != 200) {
                throw new RuntimeException("API request failed with status code: " + response.statusCode() +
                        ", Response: " + response.body());
            }

            String jsonResponse = response.body();

            return objectMapper.readValue(jsonResponse, WeatherResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching forecast: " + e.getMessage(), e);
        }
    }
}