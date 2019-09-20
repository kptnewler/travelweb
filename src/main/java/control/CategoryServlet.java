package control;

import annotation.WebUrl;
import com.alibaba.fastjson.JSON;
import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import dto.Result;
import model.Category;
import service.CategoryService;
import service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();
    @WebUrl(url = "getAll")
    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            System.out.println(category.getCname());
        }
        Result<List<Category>> categoryResult = new Result<>(categories);
        String json = JSON.toJSONString(categoryResult);
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(json);
    }
}
