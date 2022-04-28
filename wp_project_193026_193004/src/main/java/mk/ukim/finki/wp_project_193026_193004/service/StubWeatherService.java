package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.CurrentWeather;

public interface StubWeatherService {
    public CurrentWeather getCurrentWeather(String city, String country);
}
