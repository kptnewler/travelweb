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

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    @WebUrl(url = "login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equals(request.getMethod())) {
            System.out.println(request.getCharacterEncoding());
            request.getRequestDispatcher("/login.html").forward(request, response);
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean autoLogin = true;
        if (request.getParameter("auto-login") == null || request.getParameter("auto-login").isEmpty()) {
            autoLogin = false;
        }

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
                if (autoLogin) {
                    saveCookie(request, response, userWrap.getUser(), 3 * 24 * 60 * 60);
                } else {
                    saveCookie(request, response, userWrap.getUser(), -1);
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

    private void saveCookie(HttpServletRequest request, HttpServletResponse response, User user, int maxage) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", user);
        Cookie cookie = new Cookie("username", user.getUsername());
        cookie.setPath("/");
        cookie.setMaxAge(maxage);
        response.addCookie(cookie);
    }

    @WebUrl(url = "register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
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

        UserWrap userWrap = userService.register(user, request.getParameter("password"));
        @UserStatus
        int statusCode = userWrap.getUserStatus();
        Result<User> result;
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("gbk");
        response.setStatus(200);

        if (!userService.verifyCode(request.getParameter("verificationCode"),
                (String) httpSession.getAttribute("verificationCode"))) {
            result = new Result<>("验证码错误");
            response.getWriter().write(JSON.toJSONString(result));
            return;
        }

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
                break;

            default:
                result = new Result<>("注册失败");
                break;
        }

        String resultJson = JSON.toJSONString(result);
        response.getWriter().write(resultJson);
    }

    @WebUrl(url = "userinfo")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        Result<User> result;
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        if (httpSession.getAttribute("user") == null) {
            result = new Result<>("用户未登录");
        } else {
            result = new Result<User>((User) httpSession.getAttribute("user"));
        }
        response.getWriter().write(JSON.toJSONString(result));
    }

    @WebUrl(url = "sendVerificationCode")
    public void sendVerificationCode(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final OutputStream outputStream = response.getOutputStream();
        if ("POST".equals(request.getMethod())) {
            final HttpSession httpSession = request.getSession();
            final String email = request.getParameter("email");
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("gbk");
            response.setStatus(200);
            String verificationCode = null;
            try {
                verificationCode = userService.sendVerificationCode(email);
                httpSession.setAttribute("verificationCode", verificationCode);
                Result<String> result = new Result<>(verificationCode);
                result.setSuccess(true);
                outputStream.write(JSON.toJSONString(result).getBytes());
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
                Result<String> result = new Result<>("邮件发送失败");
                result.setSuccess(false);
                try {
                    outputStream.write(JSON.toJSONString(result).getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    @WebUrl(url = "logout")
    public void logout(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            Cookie[] cookies = request.getCookies();
            HttpSession httpSession = request.getSession();
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    Cookie deleteUsernameCookie = new Cookie(cookie.getName(), cookie.getValue());
                    deleteUsernameCookie.setMaxAge(0);
                    deleteUsernameCookie.setPath("/");
                    response.addCookie(deleteUsernameCookie);
                }
                if (httpSession.getId().equals(cookie.getValue())) {
                    Cookie deleteSessionIdCookie = new Cookie(cookie.getName(), cookie.getValue());
                    deleteSessionIdCookie.setMaxAge(0);
                    deleteSessionIdCookie.setPath("/");
                    response.addCookie(deleteSessionIdCookie);
                }
            }
            httpSession.invalidate();
            System.out.println("退出登录");
            request.getRequestDispatcher("/user/login").forward(request, response);
        }
    }
}
