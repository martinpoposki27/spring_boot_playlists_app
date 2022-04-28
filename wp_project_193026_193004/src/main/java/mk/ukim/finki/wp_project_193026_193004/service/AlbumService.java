package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;

import java.util.List;

public interface AlbumService {
    List<Album> listAll();
    Album findById(Long albumId);
    List<Album> findAllByArtist(Long artistId);
    Album save(String name, Genre genre, Long artistId);
    Album edit(Long id, String name, Genre genre, Long artistId);
    void deleteById(Long id);
    void like(Long id);
    List<Album> findByName(String search);

}
