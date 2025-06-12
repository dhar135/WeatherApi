package io.github.dhar135.WeatherApi.ThirdPartyWeatherApiClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dhar135.WeatherApi.model.WeatherResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Data
public class WeatherApiClient {

    @Value("${API_KEY}")
    private String apiKey;

    @Value("${API_BASE_URL}")
    private String baseUrl;

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public WeatherApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Bean
    public HttpClient getHttpClient() {
        return HttpClient.newHttpClient();
    }

    public WeatherResponse getForecast(String location, String date1, String date2) {
        //  Location is the address, partial address or latitude, longitude location for which to retrieve weather data.
        //  You can also use US ZIP Codes

        // Date1 is the start date for which to retrieve weather data

        // Date2 is the end date for which to retrieve weather data.

        // Dates should be in the format yyyy-MM-dd.
        // For example 2020-10-19 for October 19th, 2020 or 2017-02-03 for February 3rd, 2017.

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

        // Date2 can be optional for some APIs, if it's required, uncomment the following:

        try {
            // Construct the URL. The exact format depends on the third-party API.
            // This is a common pattern: /location/date1/date2?key=apiKey
            // Or: /location?date1=date1&date2=date2&key=apiKey
            // Adjust the URI construction as per the API documentation.
            String path = "/" + location + "/" + date1;
            if (date2 != null && !date2.isEmpty()) {
                path += "/" + date2;
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseUrl + path + "?key=" + apiKey))
                    .build();

            String jsonResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .join();

            return objectMapper.readValue(jsonResponse, WeatherResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching forecast: " + e.getMessage(), e);
        }
    }

}
