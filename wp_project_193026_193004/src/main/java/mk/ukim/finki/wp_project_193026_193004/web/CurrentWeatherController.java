package mk.ukim.finki.wp_project_193026_193004.web;

import mk.ukim.finki.wp_project_193026_193004.model.CurrentWeather;
import mk.ukim.finki.wp_project_193026_193004.service.LiveWeatherService;
import mk.ukim.finki.wp_project_193026_193004.service.StubWeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CurrentWeatherController {

    private final StubWeatherService stubWeatherService;
    private final LiveWeatherService liveWeatherService;

    public CurrentWeatherController(StubWeatherService stubWeatherService, LiveWeatherService liveWeatherService) {
        this.stubWeatherService = stubWeatherService;
        this.liveWeatherService = liveWeatherService;
    }

    @GetMapping("/current-weather")
    public String getCurrentWeather(Model model) {
        if (true) {
            model.addAttribute("currentWeather", liveWeatherService.getCurrentWeather("Skopje","MK"));
        } else {
            model.addAttribute("currentWeather", stubWeatherService.getCurrentWeather("Skopje","MK"));
        }
        model.addAttribute("bodyContent", "current-weather");
        return "master-template";
    }
}