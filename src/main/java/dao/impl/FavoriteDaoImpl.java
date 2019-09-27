package dao.impl;

import dao.FavoriteDao;
import model.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.sql.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean addFavoriteRoute(String rid, String uid) {
        Date date = new Date(System.currentTimeMillis());
        int count = jdbcTemplate.update("INSERT INTO tab_favorite(rid, date, uid) VALUES (?, ? ,?)",
                Integer.parseInt(rid), date,  Integer.parseInt(uid));
        if (count == 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isCollectRouteByUser(String rid, String uid) {
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM tab_favorite WHERE rid = ? AND  uid = ?", rid, uid);
        if (count == 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteFavoriteRoute(String rid, String uid) {
        int count = jdbcTemplate.update("DELETE FROM tab_favorite WHERE rid = ? AND uid = ?", rid, uid);
        if (count > 0) {
            return true;
        }

        return false;
    }
}
