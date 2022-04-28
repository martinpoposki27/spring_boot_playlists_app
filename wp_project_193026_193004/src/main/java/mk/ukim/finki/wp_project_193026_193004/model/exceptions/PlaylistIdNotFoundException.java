package mk.ukim.finki.wp_project_193026_193004.model.exceptions;

import mk.ukim.finki.wp_project_193026_193004.model.Playlist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PlaylistIdNotFoundException extends RuntimeException {
    public PlaylistIdNotFoundException(Long id) {
        super(String.format("Playlist with id: %d not found!", id));
    }
}
