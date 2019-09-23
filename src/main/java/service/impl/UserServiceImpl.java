package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import dataenum.UserStatus;
import model.User;
import model.UserWrap;
import service.UserService;
import utils.MailUtils;
import utils.UUIDUtils;

import javax.mail.MessagingException;

public class UserServiceImpl implements UserService {
    public UserDao userDao = new UserDaoImpl();

    @Override
    public UserWrap register(final User user) {
        UserWrap userWrap = new UserWrap();
        User findUser = userDao.findUserByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (findUser != null) {
            if (findUser.getUsername().equals(user.getUsername())) {
                userWrap.setUserStatus(UserStatus.USER_EXISTS);
            }
            if (findUser.getEmail().equals(user.getEmail())) {
                userWrap.setUserStatus(UserStatus.EMAIL_EXISTS);
            }
        }

        if (userDao.addUser(user)) {
            userWrap.setUserStatus(UserStatus.REGISTER_SUCCEED);
        }
        userWrap.setUserStatus(UserStatus.REGISTER_FAILED);
        return userWrap;
    }

    @Override
    public UserWrap login(String username, String password) {
        UserWrap userWrap = new UserWrap();
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            userWrap.setUserStatus(UserStatus.USER_NOT_EXISTS);
        }

        user = userDao.findUserByPassword(username, password);
        if (user == null) {
            userWrap.setUserStatus(UserStatus.USER_PASSWORD_ERROR);
        }

        userWrap.setUser(user);
        userWrap.setUserStatus(UserStatus.LOGIN_SUCCEED);
        return userWrap;
    }

    @Override
    public String sendVerificationCode(final String email) throws MessagingException {
        final String code = UUIDUtils.randomUUIDWithoutRod();

        String content = "<p>【智汇旅游网】验证码是：</p>" +
                "<p>" + code + "</p>";
        MailUtils.sendMessage(email, "智汇旅游网激活账号", content);

        return code;
    }

    @Override
    public boolean verifyCode(String userCode, String rightCode) {
        if (userCode == null || rightCode == null) {
            return false;
        }
        return userCode.equals(rightCode);
    }
}
