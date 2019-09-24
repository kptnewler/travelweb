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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();
    @WebUrl(url = "getAll")
    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean saveSessionId = false;
        HttpSession httpSession = request.getSession();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("JSESSIONID".equals(cookie.getName())) {
                saveSessionId = true;
                break;
            }
        }
        if (!saveSessionId) {
            Cookie cookie = new Cookie("JSESSIONID", httpSession.getId());
            cookie.setPath("/");
            cookie.setMaxAge(3 * 24 * 60 * 60);
            response.addCookie(cookie);
        }

        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            System.out.println(category.getCname());
        }
        Result<List<Category>> categoryResult = new Result<>(categories);
        String json = JSON.toJSONString(categoryResult);
        response.setStatus(200);
        response.setCharacterEncoding("gbk");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
