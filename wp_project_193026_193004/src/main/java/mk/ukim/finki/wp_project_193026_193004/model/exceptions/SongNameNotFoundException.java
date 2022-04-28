package mk.ukim.finki.wp_project_193026_193004.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SongNameNotFoundException extends RuntimeException {
    public SongNameNotFoundException(String name) {
        super(String.format("Song with name: %s not found!", name));
    }
}
