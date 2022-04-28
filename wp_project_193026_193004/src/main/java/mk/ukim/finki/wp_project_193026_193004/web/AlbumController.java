package mk.ukim.finki.wp_project_193026_193004.web;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;
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
@RequestMapping("/albums")
public class AlbumController {

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final UserService userService;

    public AlbumController(SongService songService, ArtistService artistService, AlbumService albumService, UserService userService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
        this.userService = userService;
    }

    @GetMapping
    public String getAlbumsPage(@RequestParam(required = false) String error, Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Album> albums = this.albumService.listAll();
        String username = request.getUserPrincipal().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("albums", albums);
        model.addAttribute("bodyContent", "albums");
        return "master-template";
    }

    @GetMapping("/add-form")
    public String showAdd(Model model) {
        List<Artist> artists = this.artistService.findAll();
        model.addAttribute("artists", artists);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("bodyContent", "add-album");
        return "master-template";
    }

    @GetMapping("/{id}/edit-form")
    public String showEdit(@PathVariable Long id, Model model) {
        Album album = this.albumService.findById(id);
        List<Artist> artists = this.artistService.findAll();
        model.addAttribute("artists", artists);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("album", album);
        model.addAttribute("bodyContent", "add-album");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveAlbum(
            @RequestParam String name,
            @RequestParam Genre genre,
            @RequestParam Long artist) {
        this.albumService.save(name, genre, artist);
        return "redirect:/albums";
    }

    @PostMapping("/add/{id}")
    public String editSong(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam Genre genre,
                           @RequestParam Long artist) {
        this.albumService.edit(id, name, genre, artist);
        return "redirect:/albums?error=AlbumNotFound";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.albumService.deleteById(id);
        return "redirect:/albums";
    }

    @PostMapping("/{id}/like")
    public String likeSong(@PathVariable Long id) {
        this.albumService.like(id);
        return "redirect:/albums";
    }

    @PostMapping("/search")
    public String searchAlbums(@RequestParam(required = false) String search, Model model, HttpServletRequest request) {
        if (search != null && !search.isEmpty()) {
            String username = request.getUserPrincipal().getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("albums", albumService.findByName(search));
            model.addAttribute("bodyContent", "albums");
            return "master-template";
        } else {
            List<Album> albums = this.albumService.listAll();
            model.addAttribute("albums", albums);
            return "redirect:/albums";
        }
    }
}
