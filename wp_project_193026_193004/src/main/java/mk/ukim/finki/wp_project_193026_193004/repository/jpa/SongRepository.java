package mk.ukim.finki.wp_project_193026_193004.repository.jpa;

import mk.ukim.finki.wp_project_193026_193004.model.Song;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByNameContainsIgnoreCase(String songName);
    List<Song> findAllByEmotion(Emotion emotion);
}
