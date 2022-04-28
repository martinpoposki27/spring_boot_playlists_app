package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
public class CurrentWeather implements Serializable {

    private String description;
    private BigDecimal temperature;
    private BigDecimal feelsLike;
    private BigDecimal windSpeed;

    public CurrentWeather() {
    }

    public CurrentWeather(String description, BigDecimal temperature, BigDecimal feelsLike, BigDecimal windSpeed) {
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentWeather that = (CurrentWeather) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(feelsLike, that.feelsLike) &&
                Objects.equals(windSpeed, that.windSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, temperature, feelsLike, windSpeed);
    }
}

