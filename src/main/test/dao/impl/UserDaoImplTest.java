package dao.impl;

import dao.UserDao;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private UserDao userDao = new UserDaoImpl();
    @Test
    public void findUserByPassword() {
    }

    @Test
    public void addUser() {
        User user = new User(10,"newler", "123456", "刘乐", "2019-08-03", "m", "1305553728", "1","1", "1");
        userDao.addUser(user, "123456");
    }

    @Test
    public void findUserByUsername() {
    }

    @Test
    public void findUserByEmail() {
    }

    @Test
    public void findUserByUsernameOrEmail() {
    }
}
