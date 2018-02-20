package weather;

import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherStationController {

    private final ConsulRegistration consul;
    private final WeatherProperties properties;

    public WeatherStationController(ConsulRegistration consul, WeatherProperties properties) {
        this.consul = consul;
        this.properties = properties;
    }

    @GetMapping("/weather")
    public Weather getWeather() {
        return new Weather(consul.getInstanceId(), properties.getTemperature(), properties.getHumidity());
    }
}
