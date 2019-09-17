package service;

import model.User;
import model.UserWrap;

public interface UserService {
    UserWrap register(User user);

    UserWrap login(String username, String password);
}
