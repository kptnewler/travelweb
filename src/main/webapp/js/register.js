layui.use(['layer', 'form', 'laydate'], function(){
    const layer = layui.layer
        , form = layui.form,
        date = layui.laydate;

    date.render({
        elem: '#test1'
    });

    form.verify({
        username: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
        },
        password: function (value, item) {
            if (!new RegExp("^[\S]{6,12}$").test(value)) {
                return '密码必须为6-12位'
            }
            if (!new RegExp("^([\s]{6,12} | [0-9]{6,12})")) {
                return '密码必须包含数字和字母'
            }
        }
    });

    form.on("summit(register)", function (data) {
        $.post("/user/register", $("form").serialize(), function (data) {
            if (data.success) {
                layui.alert("注册成功");
                location.href = "/user/index";
            } else {
                layui.alert(data.msg)
            }
        }, "json");
        return false;
    });

    $("#send-verification-code").click(function (e) {
        let email_text = $("#email").data("email");
        if (email_text === null || email_text === "") {
            alert("邮箱不能为空")
        }
        // 未被禁用
        if ($(e.target).attr("disabled")) {
            let time = 120;
            let id = setInterval(function () {
                if (time === 120) {
                    $(e.target).attr("disabled", "disabled");
                    // 发送请求
                }
                if (time === 0) {
                    $(e.target).text("发送邮箱验证码");
                    $(e.target).removeAttr("disabled");
                    return
                }
                $(e.target).text(time--);
            }, 1000);
        } else {

        }

    })

});
