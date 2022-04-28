package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.AlbumIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.ArtistIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.SongRepository;
import mk.ukim.finki.wp_project_193026_193004.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElseThrow(()-> new ArtistIdNotFoundException(id));
    }

    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public List<Artist> findAllByName(String name) {
        return artistRepository.findAllByNameContainsIgnoreCase(name);
    }

    @Override
    public Artist save(String name) {
        Artist artist = new Artist(name);
        return artistRepository.save(artist);
    }

    @Override
    public Artist edit(Long id, String name) {
        Artist artist = this.artistRepository.findById(id).orElseThrow(() -> new ArtistIdNotFoundException(id));
        artist.setName(name);
        return artistRepository.save(artist);
    }

    @Override
    public void subscribe(Long id) {
        Artist artist = this.artistRepository.findById(id).orElseThrow(() -> new AlbumIdNotFoundException(id));
        if(artist.getSubscribed()) {
            artist.setSubscribed(false);
            artistRepository.save(artist);
        } else if(!artist.getSubscribed()) {
            artist.setSubscribed(true);
            artistRepository.save(artist);
        }
    }

//    @Override
//    public List<Song> listSongs(Long artistId) {
//        Artist artist = artistRepository.findById(artistId).orElseThrow(()-> new ArtistIdNotFoundException(artistId));
//        return artist.getSongs();
//    }

//    @Override
//    public List<Album> listAlbums(Long artistId) {
//        Artist artist = artistRepository.findById(artistId).orElseThrow(()-> new ArtistIdNotFoundException(artistId));
//        return artist.getAlbums();
//    }

    @Override
    public void deleteById(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(()-> new ArtistIdNotFoundException(id));
        artistRepository.delete(artist);
    }
}
