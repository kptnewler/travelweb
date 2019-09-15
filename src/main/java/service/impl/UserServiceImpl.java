package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import dataenum.UserStatus;
import model.User;
import service.UserService;
import utils.MailUtils;

import javax.mail.MessagingException;

public class UserServiceImpl implements UserService {
    public UserDao userDao = new UserDaoImpl();
    @Override
    public int register(final User user) {
        User findUser = userDao.findUserByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (findUser != null) {
            if (findUser.getUsername().equals(user.getUsername())) {
                return UserStatus.USER_EXISTS;
            }
            if (findUser.getEmail().equals(user.getEmail())) {
                return UserStatus.EMAIL_EXISTS;
            }
        }

        if (userDao.addUser(user)) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String content = "<p>请点击下面链接激活账号，保证账号正常使用</p>" +
                                "<p><a>http://localhost:8080/activecode</a></p>";
                        MailUtils.sendMessage(user.getEmail(), "智汇旅游网激活账号", content);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return UserStatus.REGISTER_SUCCEED;
        }

        return UserStatus.REGISTER_FAILED;
    }

    @Override
    public int login(String username, String password) {
        if (userDao.findUserByUsername(username) == null) {
            return UserStatus.USER_NOT_EXISTS;
        }

        if (userDao.findUserByPassword(username, password) == null) {
            return UserStatus.USER_PASSWORD_ERROR;
        }

        return UserStatus.LOGIN_SUCCEED;
    }
}
