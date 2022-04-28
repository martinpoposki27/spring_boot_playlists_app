package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    void likeSong(String username, Long songId);
    void likeAlbum(String username, Long albumId);
    void subscribeArtist(String username, Long artistId);
    List<Song> likedSongs(String username);
    List<Song> topLikedSongs(String username);
    List<Album> likedAlbums(String username);
    List<Album> topLikedAlbums(String username);
    List<Artist> likedArtists(String username);
    List<Artist> topLikedArtists(String username);
    void updateTime(String username, Long songId);
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
