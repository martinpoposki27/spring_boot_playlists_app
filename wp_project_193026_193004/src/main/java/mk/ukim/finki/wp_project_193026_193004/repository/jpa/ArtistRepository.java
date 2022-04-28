package mk.ukim.finki.wp_project_193026_193004.repository.jpa;

import mk.ukim.finki.wp_project_193026_193004.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findAllByNameContainsIgnoreCase(String name);
}
