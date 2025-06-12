package io.github.dhar135.WeatherApi.ThirdPartyWeatherApiClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dhar135.WeatherApi.model.DailyWeather;
import io.github.dhar135.WeatherApi.model.WeatherResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WeatherApiClientTest {

    private WeatherApiClient weatherApiClient;
    private String location;
    private String date1;
    private String date2;
    private AutoCloseable closeable;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        location = "SanFrancisco,CA";
        date1 = "2025-06-05";
        date2 = "2025-06-12";

        String apiKey = "test-api-key";
        String baseUrl = "https://test-api.com";

        weatherApiClient = new WeatherApiClient(httpClient, objectMapper);
        weatherApiClient.setApiKey(apiKey);
        weatherApiClient.setBaseUrl(baseUrl);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldGetForecastWeather() {
        // Arrange
        String mockResponse = """
            {
                "latitude": 37.7749,
                "longitude": -122.4194,
                "resolvedAddress": "San Francisco, CA, USA",
                "address": "San Francisco,CA",
                "description": "Clear conditions throughout the week",
                "days": [
                    {
                        "datetime": "2025-06-05",
                        "datetimeEpoch": 1749531600,
                        "tempMax": 75.5,
                        "tempMin": 58.2,
                        "temp": 67.3,
                        "feelsLike": 66.8,
                        "feelsLikeMax": 75.0,
                        "feelsLikeMin": 58.0,
                        "dew": 52.1,
                        "humidity": 60.2,
                        "precip": 0.0,
                        "precipprob": 0,
                        "preciptype": null,
                        "windSpeed": 8.5,
                        "winddir": 270,
                        "windGust": 15.3,
                        "uvIndex": 8,
                        "description": "Sunny and clear",
                        "icon": "clear-day",
                        "sunrise": "05:47:22",
                        "sunset": "20:29:48",
                        "condition": "Clear",
                        "hours": []
                    }
                ]
            }
            """;

        CompletableFuture<HttpResponse<String>> completableFuture = CompletableFuture.completedFuture(httpResponse);

        when(httpResponse.body()).thenReturn(mockResponse);
        when(httpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandlers.ofString().getClass())))
                .thenReturn(completableFuture);

        // Act
        WeatherResponse response = weatherApiClient.getForecast(location, date1, date2);

        // Assert
        assertNotNull(response);
        assertEquals(37.7749, response.latitude());
        assertEquals(-122.4194, response.longitude());
        assertEquals("San Francisco, CA, USA", response.resolvedAddress());
        assertEquals("San Francisco,CA", response.address());

        assertNotNull(response.days());
        assertEquals(1, response.days().size());

        DailyWeather day = response.days().getFirst();
        assertEquals("2025-06-05", day.datetime());
        assertEquals(75.5, day.tempMax());
        assertEquals(58.2, day.tempMin());

        verify(httpClient).sendAsync(any(HttpRequest.class), any());
    }

    @Test
    void shouldThrowExceptionWhenLocationIsNull() {
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                weatherApiClient.getForecast(null, date1, date2));

        assertEquals("Location not set", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDateIsNull() {
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                weatherApiClient.getForecast(location, null, date2));

        assertEquals("Start date not set", exception.getMessage());
    }
}