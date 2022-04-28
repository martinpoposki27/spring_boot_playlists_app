package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.Playlist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.PlaylistIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.SongIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.PlaylistRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.SongRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.UserRepository;
import mk.ukim.finki.wp_project_193026_193004.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, SongRepository songRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Song> listAllSongsInPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistIdNotFoundException(playlistId));
        return playlist.getSongs();
    }

    @Override
    public Integer playlistDuration(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistIdNotFoundException(playlistId));
        Integer sumDuration = 0;
        for (Song song:playlist.getSongs()) {
            sumDuration = sumDuration + song.getDuration();
        }
        return sumDuration;
    }

    @Override
    public Playlist create(String name, String username) {
        //to do: da se frla exception preku userdetails
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if(user.getUsername().isEmpty()) {
            throw new UserNotFoundException(username);
        }
        Playlist playlist = new Playlist(name, user);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist addSong(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistIdNotFoundException(playlistId));
        Song song = songRepository.findById(songId).orElseThrow(() -> new SongIdNotFoundException(songId));
        List<Song> songs = playlist.getSongs();
        songs.add(song);
        playlist.setSongs(songs);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist deleteSong(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistIdNotFoundException(playlistId));
        Song song = songRepository.findById(songId).orElseThrow(() -> new SongIdNotFoundException(songId));
        List<Song> songs = playlist.getSongs();
        songs.remove(song);
        playlist.setSongs(songs);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist delete(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistIdNotFoundException(id));
        playlistRepository.delete(playlist);
        return playlist;
    }
}
