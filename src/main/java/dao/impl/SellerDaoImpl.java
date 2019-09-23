package dao.impl;

import dao.SellerDao;
import model.Seller;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller getSeller(String sid) {
        return DataAccessUtils.uniqueResult(jdbcTemplate.query("SELECT * FROM tab_seller WHERE sid = ?",
                new Object[]{sid},
                new BeanPropertyRowMapper<>(Seller.class)));
    }
}
