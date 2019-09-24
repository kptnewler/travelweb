package service;

import model.User;
import model.UserWrap;

import javax.mail.MessagingException;

public interface UserService {
    UserWrap register(User user, String password);

    UserWrap login(String username, String password);

    String sendVerificationCode(String email) throws MessagingException;

    boolean verifyCode(String userCode, String rightCode);
}
