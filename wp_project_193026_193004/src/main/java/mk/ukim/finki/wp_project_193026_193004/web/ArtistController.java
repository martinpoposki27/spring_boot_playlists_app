package mk.ukim.finki.wp_project_193026_193004.web;

import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.service.AlbumService;
import mk.ukim.finki.wp_project_193026_193004.service.ArtistService;
import mk.ukim.finki.wp_project_193026_193004.service.SongService;
import mk.ukim.finki.wp_project_193026_193004.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final UserService userService;

    public ArtistController(SongService songService, ArtistService artistService, AlbumService albumService, UserService userService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
        this.userService = userService;
    }

    @GetMapping
    public String getArtistPage(@RequestParam(required = false) String error, Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Artist> artists = this.artistService.findAll();
        String username = request.getUserPrincipal().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("artists", artists);
        model.addAttribute("bodyContent", "artists");
        return "master-template";
    }

    @GetMapping("/add-form")
    public String showAdd(Model model) {
        model.addAttribute("bodyContent", "add-artist");
        return "master-template";
    }

    @GetMapping("/{id}/edit-form")
    public String showEdit(@PathVariable Long id, Model model) {
        Artist artist = this.artistService.findById(id);
        model.addAttribute("artist", artist);
        model.addAttribute("bodyContent", "add-artist");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveArtist(@RequestParam String name) {
        this.artistService.save(name);
        return "redirect:/artists";
    }

    @PostMapping("/add/{id}")
    public String editSong(@PathVariable Long id, @RequestParam String name) {
        this.artistService.edit(id, name);
        return "redirect:/artists?error=ArtistNotFound";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.artistService.deleteById(id);
        return "redirect:/artists";
    }

    @PostMapping("/{id}/subscribe")
    public String subscribeArtist(@PathVariable Long id) {
        this.artistService.subscribe(id);
        return "redirect:/artists";
    }

    @PostMapping("/search")
    public String searchArtists(@RequestParam(required = false) String search, Model model, HttpServletRequest request) {
        if (search != null && !search.isEmpty()) {
            String username = request.getUserPrincipal().getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("artists", artistService.findAllByName(search));
            model.addAttribute("bodyContent", "artists");
            return "master-template";
        } else {
            List<Artist> artists = this.artistService.findAll();
            model.addAttribute("artists", artists);
            return "redirect:/artists";
        }
    }
}
