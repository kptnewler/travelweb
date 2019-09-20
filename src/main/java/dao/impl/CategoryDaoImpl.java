package dao.impl;

import dao.CategoryDao;
import model.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate;

    public CategoryDaoImpl() {
        jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    }

    @Override
    public List<Category> getCategories() {
        return jdbcTemplate.query("SELECT * FROM travel.tab_category", new BeanPropertyRowMapper<>(Category.class));
    }
}
