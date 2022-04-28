package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Emotion;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.AlbumIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.ArtistIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.SongIdNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.SongNameNotFoundException;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.SongRepository;
import mk.ukim.finki.wp_project_193026_193004.service.SongService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> findByName(String name) {
        List<Song> songs = songRepository.findAllByNameContainsIgnoreCase(name);
        if(songs.isEmpty()) {
            throw new SongNameNotFoundException(name);
        }
        return songs;
    }



    @Override
    public void like(Long id) {
        Song song = this.songRepository.findById(id).orElseThrow(() -> new SongIdNotFoundException(id));
        if(song.getLiked()) {
            song.setLiked(false);
            songRepository.save(song);
        } else if(!song.getLiked()) {
            song.setLiked(true);
            songRepository.save(song);
        }
    }

    @Override
    public Song findById(Long id) {
        return this.songRepository.findById(id).orElseThrow(() -> new SongIdNotFoundException(id));
    }

    @Override
    public List<Song> findAllByAlbum(Long albumId) {
        List<Song> songsOfAlbum = new ArrayList<>();
        for (Song song : songRepository.findAll()) {
            if (song.getAlbum().getId().equals(albumId)) {
                songsOfAlbum.add(song);
            }
        }
        return songsOfAlbum;
    }

    @Override
    public List<Song> findAllByArtist(Long artistId) {
        List<Song> songsofArtist = new ArrayList<>();
        for (Song song : songRepository.findAll()) {
            if (song.getArtist().getId().equals(artistId)) {
                songsofArtist.add(song);
            }
        }
        return songsofArtist;
    }

    @Override
    public Song save(String name, Integer duration, Genre genre, Emotion emotion, Long artistId, Long albumId, String link) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistIdNotFoundException(artistId));
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumIdNotFoundException(albumId));
        Song song = new Song(name, duration, genre, emotion, artist, album, link);
        return songRepository.save(song);
    }

    @Override
    public Song edit(Long id, String name, Integer duration, Genre genre, Emotion emotion, Long artistId, Long albumId, String link) {
        Song song = songRepository.findById(id).orElseThrow(() -> new SongIdNotFoundException(id));
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistIdNotFoundException(artistId));
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumIdNotFoundException(albumId));
        song.setName(name);
        song.setDuration(duration);
        song.setGenre(genre);
        song.setEmotion(emotion);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setLink(link);
        return songRepository.save(song);
    }

    @Override
    public void deleteById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new SongIdNotFoundException(id));
        songRepository.delete(song);
    }

    @Override
    public List<Song> getWeatherPlaylist(String condition) {
        if(condition.equalsIgnoreCase("clouds") || condition.equalsIgnoreCase("rain")){
            return this.songRepository.findAllByEmotion(Emotion.SAD);
        } else if(condition.equalsIgnoreCase("snow")){
            return this.songRepository.findAllByEmotion(Emotion.LOVE);
        } else {
            return this.songRepository.findAllByEmotion(Emotion.HAPPY);
        }
    }
}
