package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    Artist findById(Long id);

    List<Artist> findAll();

    public List<Artist> findAllByName(String name);

    Artist save(String name);

   // List<Song> listSongs(Long artistId);

    //List<Album> listAlbums(Long artistId);

    void deleteById(Long id);

    public Artist edit(Long id, String name);

    void subscribe(Long id);
}
