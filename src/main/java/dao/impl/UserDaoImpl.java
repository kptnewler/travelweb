package dao.impl;

import dao.UserDao;
import model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl() {
        jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    }

    @Override
    public User findUserByPassword(String username, String password) {
        return DataAccessUtils.uniqueResult(jdbcTemplate.query("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.username=? AND travel.tab_user.password=?", new Object[]{username, password},
                new BeanPropertyRowMapper<>(User.class)));
    }

    @Override
    public boolean addUser(User user, String password) {
        int count = jdbcTemplate.update("INSERT INTO" +
                " travel.tab_user(username, password, name, birthday, sex, telephone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(), password, user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail());
        return count != 0;
    }

    @Override
    public User findUserByUsername(String username) {
        return DataAccessUtils.uniqueResult(jdbcTemplate.query(
                "SELECT * FROM travel.tab_user WHERE travel.tab_user.username=?",
                new Object[]{username},
                new BeanPropertyRowMapper<>(User.class)));
    }

    @Override
    public User findUserByEmail(String email) {
        return DataAccessUtils.uniqueResult(jdbcTemplate.query("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.email=?", new Object[]{email},
                new BeanPropertyRowMapper<User>(User.class)));
    }

    @Override
    public User findUserByUsernameOrEmail(String username, String email) {
        return DataAccessUtils.uniqueResult(jdbcTemplate.query("SELECT * FROM travel.tab_user " +
                        "WHERE travel.tab_user.email=? OR travel.tab_user.username=?",
                new Object[]{email, username},  new BeanPropertyRowMapper<User>(User.class)));
    }
}
