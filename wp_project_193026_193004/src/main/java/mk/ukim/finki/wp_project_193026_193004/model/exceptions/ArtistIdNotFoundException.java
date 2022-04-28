package mk.ukim.finki.wp_project_193026_193004.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ArtistIdNotFoundException extends RuntimeException {
    public ArtistIdNotFoundException(Long id) {
        super(String.format("Artist with id: %d not found!", id));
    }
}
