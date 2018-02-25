package weather;

public class Weather {
    private final String stationId;
    private final double temperature;
    private final double humidity;

    public Weather(String stationId, double temperature, double humidity) {
        this.stationId = stationId;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getStationId() {
        return stationId;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
