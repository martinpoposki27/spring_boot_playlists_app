package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Emotion;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> findAll();

    List<Song> findByName(String name);

    void like(Long id);

    Song findById(Long id);

    List<Song> findAllByAlbum(Long albumId);

    List<Song> findAllByArtist(Long artistId);

    Song save(String name, Integer duration, Genre genre, Emotion emotion, Long artistId, Long albumId, String link);

    Song edit(Long id, String name, Integer duration, Genre genre, Emotion emotion, Long artistId, Long albumId, String link);

    void deleteById(Long id);

    public List<Song> getWeatherPlaylist(String condition);
}
