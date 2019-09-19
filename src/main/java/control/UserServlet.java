package control;

import annotation.WebUrl;
import com.alibaba.fastjson.JSON;
import dataenum.UserStatus;
import dto.Result;
import model.User;
import model.UserWrap;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    @WebUrl(url = "login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equals(request.getMethod())) {
            request.getRequestDispatcher("/login.html").forward(request, response);
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String autoLogin = request.getParameter("auto-login");

        UserWrap userWrap = userService.login(username, password);

        @UserStatus
        int userStatus = userWrap.getUserStatus();
        Result<User> result;
        switch (userStatus) {
            case UserStatus.USER_NOT_EXISTS:
                result = new Result<>("用户不存在");
                break;
            case UserStatus.USER_PASSWORD_ERROR:
                result = new Result<>("用户密码错误");
                break;
            case UserStatus.LOGIN_SUCCEED:
                if (Boolean.parseBoolean(autoLogin)) {
                    saveCookie(request, response, userWrap.getUser());
                }
                result = new Result<>(userWrap.getUser());
                break;
            default:
                result = new Result<>();
                break;
        }
        response.setStatus(200);
        String resultJson = JSON.toJSONString(result);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(resultJson);
    }

    private void saveCookie(HttpServletRequest request, HttpServletResponse response, User user) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", user);
        Cookie cookie = new Cookie("JSESSIONID", httpSession.getId());
        cookie.setMaxAge(3*24*60*60*1000);
        response.addCookie(cookie);
    }

    @WebUrl(url = "register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equals(request.getMethod())) {
            request.getRequestDispatcher("/register.html").forward(request, response);
            return;
        }

        User user = new User();
        Map<String, String[]> userMap = request.getParameterMap();
        try {
            BeanUtils.populate(user, userMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        UserWrap userWrap = userService.register(user);
        @UserStatus
        int statusCode = userWrap.getUserStatus();
        Result<User> result;
        switch (statusCode) {
            case UserStatus.USER_EXISTS:
                result = new Result<>("用户名已存在");
                break;

            case UserStatus.EMAIL_EXISTS:
                result = new Result<>("邮箱已存在");
                break;
            case UserStatus.REGISTER_FAILED:
                result = new Result<>("注册失败");
                break;
            case UserStatus.REGISTER_SUCCEED:
                result = new Result<>(userWrap.getUser());
                saveCookie(request, response, user);
                break;
            default:
                result = new Result<>();
                break;
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        String resultJson = JSON.toJSONString(result);
        response.getOutputStream().write(resultJson.getBytes());
    }

    @WebUrl(url = "userinfo")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        Result<User> result;
        response.setStatus(200);
        if (httpSession.getAttribute("user") == null) {
            result = new Result<>("用户未登录");
        } else {
            result = new Result<User>((User) httpSession.getAttribute("user"));
        }
        response.getWriter().write(JSON.toJSONString(result));
    }
 }
