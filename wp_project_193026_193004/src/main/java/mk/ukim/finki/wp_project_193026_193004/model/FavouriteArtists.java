package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class FavouriteArtists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


}
