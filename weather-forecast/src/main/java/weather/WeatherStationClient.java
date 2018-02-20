package weather;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "weather-station")
public interface WeatherStationClient {

    @GetMapping("/weather")
    Weather getWeather();

}

