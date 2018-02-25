package weather.sensors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sensors")
public class SensorsProperties {
    private double minTemperature;
    private double maxTemperature;
}
