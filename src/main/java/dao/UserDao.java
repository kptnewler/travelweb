package dao;

import model.User;

public interface UserDao {
    User getUser(String username, String password);
    boolean addUser(User user);
}
