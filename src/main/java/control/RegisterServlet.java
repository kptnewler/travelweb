package control;

import com.alibaba.fastjson.JSON;
import dataenum.UserStatus;
import dto.Result;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        Map<String, String[]> userMap = request.getParameterMap();
        try {
            BeanUtils.populate(user, userMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        @UserStatus
        int statusCode = userService.register(user);
        Result<String> result;
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
                result = new Result<>("注册成功");
                break;
            default:
                result = new Result<>();
                break;
        }
        response.setCharacterEncoding("gbk");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        String resultJson = JSON.toJSONString(result);
        response.getOutputStream().write(resultJson.getBytes());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.html").forward(request, response);
    }
}
