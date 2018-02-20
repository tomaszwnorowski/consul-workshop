package weather.sensors;

import weather.WeatherProperties;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WeatherProperties.class, SensorsProperties.class})
public class SensorsConfiguration {

    @Bean
    public HealthIndicator sensorsHealthIndicator(WeatherProperties weatherProperties, SensorsProperties sensorsProperties) {
        return new SensorsHealthIndicator(weatherProperties, sensorsProperties);
    }
}
