package weather;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("weather")
public class WeatherProperties {
    private double temperature;
    private double humidity;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
