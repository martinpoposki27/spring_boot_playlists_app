package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean liked;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

//    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
//    private List<Song> songs;

    @ManyToOne
    private Artist artist;

    public Album() {
        this.liked = false;
    }

    public Album(String name, Genre genre, Artist artist) {
        this.name = name;
        this.genre = genre;
        this.artist = artist;
        this.liked = false;
    }
}
