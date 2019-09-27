package control;

import blade.kit.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@WebFilter(filterName = "StaticResourceFilter", urlPatterns = {"*.js","*.css", "*.png", "*.jpg"})
public class StaticResourceFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest)req;
        String url = httpRequest.getRequestURI();
            String rex = "/(js|images|img|layui|css)/.+";
            Pattern pattern = Pattern.compile(rex);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                httpRequest.getRequestDispatcher(matcher.group(0)).forward(req, resp);
            }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
