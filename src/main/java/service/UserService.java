package service;

import model.User;

public interface UserService {
    int register(User user);

    int login(String username, String password);
}
