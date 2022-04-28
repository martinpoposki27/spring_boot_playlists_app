package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.Playlist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.User;

import java.util.List;

public interface PlaylistService {
    List<Song> listAllSongsInPlaylist(Long playlistId);
    Integer playlistDuration(Long playlistId);
    Playlist create(String name, String username);
    Playlist addSong(Long playlistId, Long songId);
    Playlist deleteSong(Long playlistId, Long songId);
    Playlist delete(Long id);

}
