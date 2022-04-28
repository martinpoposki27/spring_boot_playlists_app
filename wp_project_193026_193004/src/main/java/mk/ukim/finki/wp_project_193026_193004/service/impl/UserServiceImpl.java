package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Role;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.*;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.SongRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.UserRepository;
import mk.ukim.finki.wp_project_193026_193004.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AlbumRepository albumRepository, ArtistRepository artistRepository, SongRepository songRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void likeSong(String username, Long songId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Song song = songRepository.findById(songId).orElseThrow(() -> new SongIdNotFoundException(songId));
        if(user.getFavouriteSongs().contains(song)){
            user.getFavouriteSongs().remove(song);
        } else {
            user.getFavouriteSongs().add(song);
        }
        userRepository.save(user);
    }

    @Override
    public void likeAlbum(String username, Long albumId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumIdNotFoundException(albumId));
        if(user.getFavouriteAlbums().contains(album)){
            user.getFavouriteAlbums().remove(album);
        } else {
            user.getFavouriteAlbums().add(album);
        }
        userRepository.save(user);
    }

    @Override
    public void subscribeArtist(String username, Long artistId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistIdNotFoundException(artistId));
        if(user.getFavouriteArtists().contains(artist)){
            user.getFavouriteArtists().remove(artist);
        } else {
            user.getFavouriteArtists().add(artist);
        }
        userRepository.save(user);
    }

    @Override
    public List<Song> likedSongs(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getFavouriteSongs();
    }

    @Override
    public List<Song> topLikedSongs(String username) {
        List<Song> topSongs = new ArrayList<>();
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        for(int i = 0; i < 10; i++) {
            topSongs.add(user.getFavouriteSongs().get(i));
        }
        return topSongs;
    }

    @Override
    public List<Album> likedAlbums(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getFavouriteAlbums();
    }

    @Override
    public List<Album> topLikedAlbums(String username) {
        return null;
    }

    @Override
    public List<Artist> likedArtists(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getFavouriteArtists();
    }

    @Override
    public List<Artist> topLikedArtists(String username) {
        return null;
    }

    @Override
    public void updateTime(String username, Long songId) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Song song = this.songRepository.findById(songId).orElseThrow(() -> new SongIdNotFoundException(songId));
        user.setTimeSpent(user.getTimeSpent() + (song.getDuration()/60));
        userRepository.save(user);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if (this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
