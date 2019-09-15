package dao.impl;

import dao.UserDao;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl() {
        jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    }

    @Override
    public User findUserByPassword(String username, String password) {
        return jdbcTemplate.queryForObject("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.name=? AND travel.tab_user.password=?", new Object[]{username, password}, User.class);
    }

    @Override
    public boolean addUser(User user) {
        int count = jdbcTemplate.update("INSERT INTO" +
                " travel.tab_user(username, password, name, birthday, sex, telephone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
        return count != 0;
    }

    @Override
    public User findUserByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.username=?", new Object[]{username}, User.class);
    }

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.email=?", new Object[]{email}, User.class);
    }

    @Override
    public User findUserByUsernameOrEmail(String username, String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM travel.tab_user " +
                        "WHERE travel.tab_user.email=? OR travel.tab_user.username=?",
                new Object[]{email, username}, User.class);
    }
}
