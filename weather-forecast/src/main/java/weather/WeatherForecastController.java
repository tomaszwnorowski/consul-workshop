package weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;

    public WeatherForecastController(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping("/forecast")
    public WeatherForecast getForecast() {
        return weatherForecastService.getForecast();
    }

}
