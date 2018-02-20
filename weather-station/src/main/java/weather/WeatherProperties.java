package weather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("weather")
public class WeatherProperties {
    private double temperature;
    private double humidity;
}
