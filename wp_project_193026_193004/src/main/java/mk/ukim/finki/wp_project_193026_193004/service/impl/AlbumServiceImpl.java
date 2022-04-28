package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.AlbumIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.ArtistIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.SongIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp_project_193026_193004.service.AlbumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Album> listAll() {
        return this.albumRepository.findAll();
    }

    @Override
    public Album findById(Long albumId) {
        return this.albumRepository.findById(albumId).orElseThrow(() -> new AlbumIdNotFoundException(albumId));
    }

    @Override
    public List<Album> findAllByArtist(Long artistId) {
        Artist artist = this.artistRepository.findById(artistId).orElseThrow(() -> new AlbumIdNotFoundException(artistId));
        return this.albumRepository.findAllByArtist(artist);
    }

    @Override
    public Album save(String name, Genre genre, Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistIdNotFoundException(artistId));
        Album album = new Album(name, genre, artist);
        return albumRepository.save(album);
    }

    @Override
    public Album edit(Long id, String name, Genre genre, Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistIdNotFoundException(artistId));
        Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumIdNotFoundException(id));
        album.setName(name);
        album.setGenre(genre);
        album.setArtist(artist);
        return albumRepository.save(album);
    }

    @Override
    public void deleteById(Long id) {
        albumRepository.findById(id).orElseThrow(() -> new AlbumIdNotFoundException(id));
        this.albumRepository.deleteById(id);
    }

    @Override
    public void like(Long id) {
        Album album = this.albumRepository.findById(id).orElseThrow(() -> new AlbumIdNotFoundException(id));
        if(album.getLiked()) {
            album.setLiked(false);
            albumRepository.save(album);
        } else if(!album.getLiked()) {
            album.setLiked(true);
            albumRepository.save(album);
        }
    }

    @Override
    public List<Album> findByName(String search) {
        return this.albumRepository.findAllByNameContainsIgnoreCase(search);
    }

}
