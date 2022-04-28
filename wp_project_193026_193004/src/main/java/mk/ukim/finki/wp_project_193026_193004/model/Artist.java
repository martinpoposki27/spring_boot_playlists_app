package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean subscribed;

//    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
//    private List<Album> albums;

    public Artist() {
        this.subscribed = false;
    }

    public Artist(String name) {
        this.subscribed = false;
        this.name = name;
    }
}
