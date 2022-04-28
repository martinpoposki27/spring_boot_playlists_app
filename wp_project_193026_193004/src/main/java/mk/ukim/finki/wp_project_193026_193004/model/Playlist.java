package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Song> songs;

    @ManyToOne
    private User user;

    public Playlist() {
    }

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
