package mk.ukim.finki.wp_project_193026_193004.service.impl;

import mk.ukim.finki.wp_project_193026_193004.model.User;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp_project_193026_193004.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp_project_193026_193004.repository.jpa.UserRepository;
import mk.ukim.finki.wp_project_193026_193004.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }


}
