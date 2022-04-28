package mk.ukim.finki.wp_project_193026_193004.model;

import lombok.Data;
import mk.ukim.finki.wp_project_193026_193004.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "playlist_user")
public class User implements UserDetails {

    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    private Integer timeSpent;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Playlist> playlists;

    @ManyToMany
    private List<Artist> favouriteArtists;

    @ManyToMany
    private List<Album> favouriteAlbums;

    @ManyToMany
    private List<Song> favouriteSongs;

    public User() {
        this.timeSpent = 0;
    }

    public User(String username, String password, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.timeSpent = 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    public Boolean ifLiked(Song song) {
        return (this.favouriteSongs.contains(song));
    }
    public Boolean ifLikedAlbum(Album album) {
        return (this.favouriteAlbums.contains(album));
    }
    public Boolean ifSubscribed(Artist artist) {
        return (this.favouriteArtists.contains(artist));
    }

}
