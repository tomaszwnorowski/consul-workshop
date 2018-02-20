package weather.sensors;

import weather.WeatherProperties;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

public class SensorsHealthIndicator implements HealthIndicator {

    private final WeatherProperties weatherProperties;
    private final SensorsProperties sensorsProperties;

    public SensorsHealthIndicator(WeatherProperties weatherProperties, SensorsProperties sensorsProperties) {
        this.weatherProperties = weatherProperties;
        this.sensorsProperties = sensorsProperties;
    }

    @Override
    public Health health() {
        // TODO
        // If current temperature (weatherProperties.temperature) exceeds any of sensors thresholds
        // (sensors.minTemperature, sensors.maxTemperature) then return status DOWN with appropriate
        // message (details), otherwise return status UP.
        return Health.status(Status.UP).build();
    }
}
