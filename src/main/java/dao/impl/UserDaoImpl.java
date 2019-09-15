package dao.impl;

        import com.mysql.cj.jdbc.JdbcConnection;
        import dao.UserDao;
        import model.User;
        import org.springframework.jdbc.core.BeanPropertyRowMapper;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.jdbc.core.PreparedStatementCreator;
        import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
        import utils.JDBCUtils;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.SQLException;
        import java.sql.Statement;

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
       int count =  jdbcTemplate.update("INSERT INTO" +
                " travel.tab_user(username, password, name, birthday, sex, telephone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
        return count != 0;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.username=?", new Object[]{username}, User.class);
        return user;
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
