package weather.sensors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sensors")
public class SensorsProperties {
    private double minTemperature;
    private double maxTemperature;
}
