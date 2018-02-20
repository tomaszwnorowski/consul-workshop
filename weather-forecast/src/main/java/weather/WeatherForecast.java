package weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {
    private double temperature;
    private double feelsLikeTemperature;
    private String description;
}
