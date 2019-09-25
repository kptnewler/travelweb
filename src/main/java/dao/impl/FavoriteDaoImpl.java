package dao.impl;

import dao.FavoriteDao;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.sql.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean addFavoriteRoute(String rid, String uid) {
        Date date = new Date(System.currentTimeMillis());
        int count = jdbcTemplate.update("INSERT INTO tab_favorite(rid, date, uid) VALUES (?, ? ,?)", rid, uid, date);
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }
}
