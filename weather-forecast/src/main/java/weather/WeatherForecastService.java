package weather;

import org.springframework.stereotype.Service;

@Service
public class WeatherForecastService {

    private final WeatherStationClient weatherStationClient;

    public WeatherForecastService(WeatherStationClient weatherStationClient) {
        this.weatherStationClient = weatherStationClient;
    }

    public WeatherForecast getForecast() {
        final Weather weather = weatherStationClient.getWeather();
        return new WeatherForecast(weather.getTemperature(),
                weather.getTemperature() * weather.getHumidity(),
                "forecast based on: " + weather.getStationId()
        );
    }

}
