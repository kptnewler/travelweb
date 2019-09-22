package dao.impl;

import dao.RouteDao;
import model.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Route> getPageRoutesByCid(String cid, int startIndex, int pageCount) {
        return jdbcTemplate.query("SELECT * FROM " +
                        "travel.tab_route, travel.tab_seller, travel.tab_category " +
                        "WHERE " +
                        "tab_seller.sid=tab_route.sid AND  " +
                        "tab_category.cid = tab_route.cid AND tab_route.cid = ? LIMIT ?,?;",
                new Object[]{cid, startIndex, pageCount},
                new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public int getRoutesCountByCID(String cid) {
        return jdbcTemplate.queryForInt("SELECT COUNT(*) FROM travel.tab_route WHERE tab_route.cid = ?",
                cid);
    }
}
