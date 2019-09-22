package dao.impl;

import dao.RouteDao;
import model.Route;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Route> getPageRoutesByCid(String cid, int startIndex, int pageCount) {
        return jdbcTemplate.query("SELECT * FROM " +
                        "travel.tab_route " +
                        "WHERE " +
                        "tab_route.cid = ? LIMIT ?,?;",
                new Object[]{cid, startIndex, pageCount},
                new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public int getRoutesCountByCid(String cid) {
        return jdbcTemplate.queryForInt("SELECT COUNT(*) FROM travel.tab_route WHERE tab_route.cid = ?",
                cid);
    }


    @Override
    public Route getRouteInfoById(String rid) {
        List<Route> routes =  jdbcTemplate.query(
                "SELECT DISTINCT * FROM tab_category, tab_seller, tab_route, tab_route_img" +
                        " WHERE " +
                        "tab_route.cid = tab_category.cid " +
                        "AND " +
                        "tab_seller.sid = tab_route.sid AND tab_route.rid = ? AND tab_route_img.rid = tab_route.rid"
                , new Object[]{rid},
                new BeanPropertyRowMapper<>(Route.class));
        if (routes != null && !routes.isEmpty()) {
            return routes.get(0);
        }
        return null;
    }
}
