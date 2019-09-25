function getCookie(cname) {
    let name = cname + "=";
    const keyValues = document.cookie.split(";");
    for (let i = 0; i < keyValues.length; i++) {
        const c = keyValues[i].trim();
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function getCategories(element) {
    $.getJSON("/category/getAll", function (result) {
        if (result.success) {
            let nav_items = "<li class=layui-nav-item><a href=''>首页</a></li>\n";
            for (let i = 0; i < result.data.length; i++) {
                nav_items += "<li class=layui-nav-item><a href=''>" + result.data[i].cname + "</a></li>";
            }
            let username = getCookie("username");
            if (username.length === 0) {
                $(".layui-breadcrumb").show();
            } else  {
                let user_login_item = "<li class=\"layui-nav-item\">\n" +
                    "                <a href=\"javascript:;\">"+username+"</a>\n" +
                    "                <dl class=\"layui-nav-child\">\n" +
                    "                    <dd><a href=\"#\">修改信息</a></dd>\n" +
                    "                    <dd><a href=\"#\">安全管理</a></dd>\n" +
                    "                    <dd><a href=\"#\">激活账号</a></dd>\n" +
                    "                    <dd id='login-out'><a href=\"\\user\\logout\">退出登录</a></dd>\n" +
                    "                </dl>\n" +
                    "            </li>";
                nav_items += user_login_item;
            }
            $(".layui-nav").html(nav_items);
            element.render("nav");
        } else {
            alert("获取失败")
        }
    });

}


