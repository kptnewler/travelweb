package dao.impl;

import dao.RouteImgDao;
import model.RouteImg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> getRouteImgs(String rid) {
        return jdbcTemplate.query("SELECT * FROM tab_route_img WHERE rid = ?",
                new Object[]{rid},
                new BeanPropertyRowMapper<>(RouteImg.class));
    }
}
