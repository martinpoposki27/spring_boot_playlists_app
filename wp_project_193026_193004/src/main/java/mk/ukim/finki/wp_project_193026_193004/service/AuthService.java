package mk.ukim.finki.wp_project_193026_193004.service;

import mk.ukim.finki.wp_project_193026_193004.model.User;

public interface AuthService {
    User login(String username, String password);
}
