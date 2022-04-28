package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.CurrentWeather;

public interface LiveWeatherService {
    public CurrentWeather getCurrentWeather(String city, String country);

    String getCurrentWeatherCondition(String city, String country);
}
