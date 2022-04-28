package mk.ukim.finki.wp_project_193026_193004.web;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Emotion;
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
@RequestMapping("/songs")
public class SongsController {

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final UserService userService;

    public SongsController(SongService songService, ArtistService artistService, AlbumService albumService, UserService userService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
        this.userService = userService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String search, @RequestParam(required = false) String error, Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = request.getUserPrincipal().getName();
        User user = userService.findByUsername(username);


        if(search != null && !search.isEmpty()) {
            model.addAttribute("songs", this.songService.findByName(search));
        } else {
            model.addAttribute("songs", this.songService.findAll());
        }
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "songs");
        return "master-template";
    }

//    @PostMapping
//    public String postSongs(@RequestParam(required = false) String search, Model model) {
//        if (search != null) {
//            model.addAttribute("songs", songService.findByName(search));
//            model.addAttribute("bodyContent", "songs");
//            return "master-template";
//        } else {
//            List<Song> songs = this.songService.findAll();
//            model.addAttribute("songs", songs);
//            return "redirect:/songs";
//        }
//    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAdd(Model model) {
        List<Artist> artists = this.artistService.findAll();
        List<Album> albums = this.albumService.listAll();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("emotions", Emotion.values());
        model.addAttribute("bodyContent", "add-song");
        return "master-template";
    }

    @GetMapping("/{id}/edit-form")
    public String showEdit(@PathVariable Long id, Model model) {
        Song song = this.songService.findById(id);
        List<Artist> artists = this.artistService.findAll();
        List<Album> albums = this.albumService.listAll();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("emotions", Emotion.values());
        model.addAttribute("song", song);
        model.addAttribute("bodyContent", "add-song");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveSong(
            @RequestParam String name,
            @RequestParam Integer duration,
            @RequestParam Genre genre,
            @RequestParam Emotion emotion,
            @RequestParam Long artist,
            @RequestParam Long album,
            @RequestParam String link) {
        this.songService.save(name, duration, genre, emotion, artist, album, link);
        return "redirect:/songs";
    }

    @PostMapping("/add/{id}")
    public String editSong(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam Integer duration,
                           @RequestParam Genre genre,
                           @RequestParam Emotion emotion,
                           @RequestParam Long artist,
                           @RequestParam Long album,
                           @RequestParam String link) {
        this.songService.edit(id, name, duration, genre, emotion, artist, album, link);
        return "redirect:/songs?error=SongNotFound";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.songService.deleteById(id);
        return "redirect:/songs";
    }

    @PostMapping("/{id}/like")
    public String likeSong(@PathVariable Long id) {
        this.songService.like(id);
        return "redirect:/songs";
    }

    @PostMapping("/search")
    public String searchSongs(@RequestParam(required = false) String search, Model model, HttpServletRequest request) {
        if (search != null && !search.isEmpty()) {
            String username = request.getUserPrincipal().getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
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
    public String getSongsOfAlbum(@RequestParam(required = false) String error, @PathVariable Long id, Model model, HttpServletRequest request) {
        albumService.findById(id);
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = request.getUserPrincipal().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("songs", songService.findAllByAlbum(id));
        model.addAttribute("bodyContent", "songs");
        return "master-template";
    }

    @PostMapping("/{id}/artist")
    public String getSongsByArist(@RequestParam(required = false) String error, @PathVariable Long id, Model model, HttpServletRequest request) {
        artistService.findById(id);
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = request.getUserPrincipal().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("songs", songService.findAllByArtist(id));
        model.addAttribute("bodyContent", "songs");
        return "master-template";
    }
}
