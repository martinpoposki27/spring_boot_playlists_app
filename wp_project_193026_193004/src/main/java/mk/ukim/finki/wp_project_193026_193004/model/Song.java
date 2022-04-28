package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Emotion;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Genre;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer duration;

    private String link;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Enumerated(value = EnumType.STRING)
    private Emotion emotion;

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Album album;

    private Boolean liked;

    public Song() {
        liked = false;
    }

    public Song(String name, Integer duration, Genre genre, Emotion emotion, Artist artist, Album album, String link) {
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.emotion = emotion;
        this.artist = artist;
        this.album = album;
        this.liked = false;
        this.link = link;
    }

    public String getInMinutes() {
        String minutes = String.valueOf(duration/60);
        String seconds = String.valueOf(duration%60);
        return minutes + ":" + seconds;
    }
}
