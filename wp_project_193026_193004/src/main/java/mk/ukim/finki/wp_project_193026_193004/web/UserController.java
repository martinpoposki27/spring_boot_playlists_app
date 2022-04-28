package mk.ukim.finki.wp_project_193026_193004.web;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;
import mk.ukim.finki.wp_project_193026_193004.service.AlbumService;
import mk.ukim.finki.wp_project_193026_193004.service.ArtistService;
import mk.ukim.finki.wp_project_193026_193004.service.SongService;
import mk.ukim.finki.wp_project_193026_193004.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final UserService userService;

    public UserController(SongService songService, ArtistService artistService, AlbumService albumService, UserService userService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
        this.userService = userService;
    }

    @PostMapping("/{id}/like")
    public String likeSong(@PathVariable Long id, Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        this.userService.likeSong(username, id);
        return "redirect:/songs";
    }

    @PostMapping("/{id}/play")
    public String playSong(@PathVariable Long id, Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Song song = this.songService.findById(id);
        userService.updateTime(username, id);
        return "redirect:"+song.getLink();
    }

    @PostMapping("/{id}/likeAlbum")
    public String likeAlbum(@PathVariable Long id, Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        this.userService.likeAlbum(username, id);
        return "redirect:/albums";
    }

    @PostMapping("/{id}/subscribe")
    public String subscribeArtist(@PathVariable Long id, Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        this.userService.subscribeArtist(username, id);
        return "redirect:/artists";
    }

    @PostMapping("/search")
    public String searchSongs(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("songs", songService.findByName(search));
            model.addAttribute("bodyContent", "songs");
            return "master-template";
        } else {
            List<Song> songs = this.songService.findAll();
            model.addAttribute("songs", songs);
            return "redirect:/songs";
        }
    }

    @PostMapping("/{id}/album")
    public String getSongsOfAlbum(@RequestParam(required = false) String error, @PathVariable Long id, Model model) {
        albumService.findById(id);
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("songs", songService.findAllByAlbum(id));
        model.addAttribute("bodyContent", "songs");
        return "master-template";
    }

    @PostMapping("/{id}/artist")
    public String getSongsByArist(@RequestParam(required = false) String error, @PathVariable Long id, Model model) {
        artistService.findById(id);
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("songs", songService.findAllByArtist(id));
        model.addAttribute("bodyContent", "songs");
        return "master-template";
    }
}
