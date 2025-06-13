package io.github.dhar135.WeatherApi.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitingFilter implements Filter {

    // Map to store the number of requests per IP address
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    // Maximum number of requests per IP address
    private final static int MAX_REQUESTS_PER_MINUTE = 5;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the IP address of the request
        String ipAddress = httpRequest.getRemoteAddr();

        // Initialize the request count for the IP address if it doesn't exist
        requestCounts.putIfAbsent(ipAddress, new AtomicInteger(0));
        AtomicInteger requestCount = requestCounts.get(ipAddress);

        // Increment the request count
        int requests = requestCount.incrementAndGet();

        // Check if the request limit has been exceeded
        if (requests > MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Too many requests");
            return;
        }

        // Reset request counts periodically
        if (requests % MAX_REQUESTS_PER_MINUTE == 0) {
            requestCounts.remove(ipAddress);
        }

        // Set the request count in the response header
        httpResponse.setHeader("X-Rate-Limit-Remaining", String.valueOf(MAX_REQUESTS_PER_MINUTE - requests));

        // Continue with the request
        chain.doFilter(request, response);
    }

}
