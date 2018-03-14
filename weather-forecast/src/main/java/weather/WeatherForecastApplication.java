package weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
public class WeatherForecastApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastApplication.class, args);
    }

}
