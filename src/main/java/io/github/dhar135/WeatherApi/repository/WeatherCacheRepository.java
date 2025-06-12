package io.github.dhar135.WeatherApi.repository;

import io.github.dhar135.WeatherApi.model.WeatherResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// The key type (String) is for the key used in Redis, the value type is your model (WeatherResponse)
@Repository
public interface WeatherCacheRepository extends CrudRepository<WeatherResponse, String> {

}
