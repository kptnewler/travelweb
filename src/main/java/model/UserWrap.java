package model;

import dataenum.UserStatus;

public class UserWrap  {
    @UserStatus
    private int userStatus;

    private User user;


    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
