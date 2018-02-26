package weather.sensors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sensors")
public class SensorsProperties {

    private double minTemperature;
    private double maxTemperature;

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

}
