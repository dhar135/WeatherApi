package io.github.dhar135.WeatherApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import io.github.dhar135.WeatherApi.config.RateLimitingFilter;

@EnableCaching
@SpringBootApplication
public class WeatherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);

	}

	@Bean
	public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter() {
		FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new RateLimitingFilter());
		registration.addUrlPatterns("/api/weather/*");
		return registration;
	}

}