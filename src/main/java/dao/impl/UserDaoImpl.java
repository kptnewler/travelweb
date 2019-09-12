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

    @Override
    public User getUser(String username, String password) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        User user = jdbcTemplate.queryForObject("SELECT * FROM travel.tab_user " +
                "WHERE travel.tab_user.name=? AND travel.tab_user.password=?", new Object[]{username, password}, User.class);
        return user;
    }

    @Override
    public boolean addUser(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

        return false;
    }
}
