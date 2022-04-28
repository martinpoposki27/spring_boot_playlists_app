package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.CurrentWeather;
import mk.ukim.finki.wp_project_193026_193004.service.StubWeatherService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StubWeatherServiceImpl implements StubWeatherService {

    public CurrentWeather getCurrentWeather(String city, String country) {
        return new CurrentWeather("Clear", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
    }
}
