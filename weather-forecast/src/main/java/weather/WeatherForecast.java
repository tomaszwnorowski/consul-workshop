package weather;

public class WeatherForecast {
    private double temperature;
    private double feelsLikeTemperature;
    private String description;

    public WeatherForecast(double temperature, double feelsLikeTemperature, String description) {
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public void setFeelsLikeTemperature(double feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
