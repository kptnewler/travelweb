package control;

import blade.kit.http.HttpRequest;
import dataenum.UserStatus;
import model.User;
import model.UserWrap;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter", urlPatterns = {"/*"})
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        User user = (User) httpSession.getAttribute("user");
        Cookie[] cookies = httpRequest.getCookies();
        String username = "";
        String password = "";
        if (user != null) {
            chain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                username = cookie.getValue();
                if (username == null) username = "";
            }

            if ("password".equals(cookie.getName())) {
                password = cookie.getValue();
                if (password == null) password = "";
            }
        }
        System.out.println(username+"-"+password);
        if (!username.isEmpty() && !password.isEmpty()) {
            UserService userService = new UserServiceImpl();
            UserWrap userWrap = userService.login(username, password);
            if (userWrap.getUserStatus() == UserStatus.LOGIN_SUCCEED) {
                httpSession.setAttribute("user", userWrap.getUser());
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
