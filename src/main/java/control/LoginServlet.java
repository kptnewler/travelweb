package control;

import com.alibaba.fastjson.JSON;
import dataenum.UserStatus;
import dto.Result;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String autoLogin = request.getParameter("auto-login");

        @UserStatus
        int userStatus = userService.login(username, password).getUserStatus();
        Result<String> result;
        switch (userStatus) {
            case UserStatus.USER_NOT_EXISTS:
                result = new Result<>("用户不存在");
                break;
            case UserStatus.USER_PASSWORD_ERROR:
                result = new Result<>("用户密码错误");
                break;
            case UserStatus.LOGIN_SUCCEED:
                if (Boolean.parseBoolean(autoLogin))
                    saveCookie(request, response);
                result = new Result<>("登录成功");
                break;
            default:
                result = new Result<>();
                break;
        }
        response.setStatus(200);
        String resultJson = JSON.toJSONString(result);
        response.setContentType("application/json;charset=utf-8");
        response.getOutputStream().write(resultJson.getBytes());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.html").forward(request, response);
    }

    private void saveCookie(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("username", username);
        httpSession.setAttribute("password", password);
        Cookie cookie = new Cookie("JSESSIONID", httpSession.getId());
        cookie.setMaxAge(3*24*60*60*1000);
        response.addCookie(cookie);
    }
}
