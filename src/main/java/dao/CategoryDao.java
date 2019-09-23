package dao;

import model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategories();
    Category getCategoryByCid(String cid);
}

