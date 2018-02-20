package weather;

import lombok.Data;

@Data
public class Weather {
    private final String stationId;
    private final double temperature;
    private final double humidity;
}
