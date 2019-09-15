package dao;

import model.User;

public interface UserDao {
    User findUserByPassword(String username, String password);
    boolean addUser(User user);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByUsernameOrEmail(String username, String email);
}
