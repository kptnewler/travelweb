package dataenum;

public @interface UserStatus {
    // 用户名或密码错误
    int USER_OR_PASSWORD = -1;

    // 用户找不到
    int USER_NOT_EXISTS = -2;

    // 用户已存在
    int USER_EXISTS = -3;

    // 邮箱已存在
    int EMAIL_EXISTS = -4;

    // 注册失败
    int REGISTER_FAILED = -5;

    // 未激活
    int USER_NOT_ACTIVATION = -6;

    // 注册成功
    int REGISTER_SUCCEED = 1;

    // 登录成功
    int LOGIN_SUCCEED = 2;
}
