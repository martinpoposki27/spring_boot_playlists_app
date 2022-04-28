package mk.ukim.finki.wp_project_193026_193004.repository.jpa;

import mk.ukim.finki.wp_project_193026_193004.model.Album;
import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import mk.ukim.finki.wp_project_193026_193004.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByArtist(Artist artist);
    List<Album> findAllByNameContainsIgnoreCase(String songName);
}
