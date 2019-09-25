package control;

import annotation.WebUrl;
import com.alibaba.fastjson.JSON;
import dto.Page;
import dto.Result;
import dto.RouteDetails;
import model.Route;
import model.User;
import service.RouteService;
import service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    @WebUrl(url = "list")
    public void routeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result<Page<Route>> result;
        int pageSize = 10, currentPage = 1;
        String pageSizeString = request.getParameter("pageSize");
        String currentPageString = request.getParameter("currentPage");
        String cid = request.getParameter("cid");
        if (pageSizeString != null && !pageSizeString.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeString);
        }
        if (currentPageString != null && !currentPageString.isEmpty()) {
            currentPage = Integer.parseInt(currentPageString);
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.setCharacterEncoding("gbk");
        Page<Route> routePage = routeService.getRoutesByCid(cid, currentPage, pageSize);
        result = new Result<>(routePage);
        String json = JSON.toJSONString(result);
        response.getWriter().write(json);
    }

    @WebUrl(url = "all")
    public void routeAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/routelist.html").forward(request, response);
    }

    @WebUrl(url = "detail")
    public void routeDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RouteDetails routeDetails = routeService.getRouteInfoById(request.getParameter("rid"));
        Result<RouteDetails> result = new Result<>(routeDetails);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.setCharacterEncoding("gbk");

        String json = JSON.toJSONString(result);
        response.getWriter().write(json);
    }

    @WebUrl(url = "collect")
    public void collectRoute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String rid = request.getParameter("rid");
        Cookie[] cookies = request.getCookies();
        HttpSession httpSession = request.getSession();
        Result<Route> result;
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            Route route = routeService.collectRoute(String.valueOf(user.getUid()), rid);
            if (route == null) {
                result = new Result<>("收藏失败");
            } else {
                result = new Result<>(route);
            }
        } else {
            result = new Result<>("请登录后重试");
        }
        String json = JSON.toJSONString(result);
        response.getWriter().write(json);
    }
}
