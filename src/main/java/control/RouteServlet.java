package control;

import annotation.WebUrl;
import com.alibaba.fastjson.JSON;
import dto.Page;
import dto.Result;
import model.Route;
import service.RouteService;
import service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
