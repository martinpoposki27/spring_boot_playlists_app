package mk.ukim.finki.wp_project_193026_193004.repository.jpa;

import mk.ukim.finki.wp_project_193026_193004.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
