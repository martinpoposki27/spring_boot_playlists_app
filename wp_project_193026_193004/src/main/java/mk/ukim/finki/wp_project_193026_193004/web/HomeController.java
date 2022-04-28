package mk.ukim.finki.wp_project_193026_193004.web;

import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.service.LiveWeatherService;
import mk.ukim.finki.wp_project_193026_193004.service.SongService;
import mk.ukim.finki.wp_project_193026_193004.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final UserService userService;
    private final LiveWeatherService weatherService;
    private final SongService songService;

    public HomeController(UserService userService, LiveWeatherService weatherService, SongService songService) {
        this.userService = userService;
        this.weatherService = weatherService;
        this.songService = songService;
    }

    @GetMapping
    public String getHomePageAuth(HttpServletRequest request, Model model) {
        if(request.getUserPrincipal().getName() != null) {
            String username = request.getUserPrincipal().getName();
            User user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("likedSongs", userService.likedSongs(username));
            model.addAttribute("likedAlbums", userService.likedAlbums(username));
            model.addAttribute("likedArtists", userService.likedArtists(username));
            model.addAttribute("weatherSongs", songService.getWeatherPlaylist(weatherService.getCurrentWeather("Skopje", "MK").getDescription()));
            model.addAttribute("condition", weatherService.getCurrentWeatherCondition("Skopje", "MK"));
            model.addAttribute("city", "Skopje");
        }
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }

    @PostMapping("/current-weather")
    public String postCurrentWeather(@RequestParam(required = false) String city, Model model, HttpServletRequest request) {
        if (city != null && !city.isEmpty()) {
            String username = request.getUserPrincipal().getName();
            User user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("weatherSongs", songService.getWeatherPlaylist(weatherService.getCurrentWeather(city, "MK").getDescription()));
            model.addAttribute("condition", weatherService.getCurrentWeather(city, "MK").getDescription());
            model.addAttribute("city", city);
        } else {
            String username = request.getUserPrincipal().getName();
            User user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("weatherSongs", songService.getWeatherPlaylist(weatherService.getCurrentWeather("Skopje", "MK").getDescription()));
            model.addAttribute("condition", weatherService.getCurrentWeather("Skopje", "MK").getDescription());
            model.addAttribute("city", "Skopje");
        }
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

}
