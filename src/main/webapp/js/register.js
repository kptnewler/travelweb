layui.use(['layer', 'form', 'laydate'], function(){
    const layer = layui.layer
        , form = layui.form,
        date = layui.laydate;

    date.render({
        elem: '#date'
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
            if (!new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$")) {
                return '密码为6-12位，必须包含数字和字母'
            }
        }
    });


    form.on('submit(*)', function (data) {
        $.post("/user/register", $("form").serialize(), function (data) {
            if (data.success) {
                alert("注册成功，跳转登录");
                location.href = "/user/login";
            } else {
                alert(data.msg)
            }
        }, "json");
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    $("#send-verification-code").click(function (e) {
        let email_text = $("#email").val();
        if (email_text === null || email_text === "") {
            alert("邮箱不能为空");
            return
        }
        // 未被禁用
        if ($(e.target).attr("disabled") === undefined) {
            let time = 5;
            // 发送请求
            $.post("/user/sendVerificationCode", {email:email_text}, function (result) {
                alert(JSON.stringify(result));
                if (result.success) {
                    timeDown(e, time);
                } else {
                    alert("邮件发送失败");
                }
            });
        }
        return false;
    })

});

function timeDown(e, time) {
    $(e.target).attr("disabled", "disabled");
    $(e.target).addClass("layui-btn-disabled");
    let id = setInterval(function () {
        if (time === 0) {
            $(e.target).text("发送邮箱验证码");
            $(e.target).removeAttr("disabled");
            $(e.target).removeClass("layui-btn-disabled");
            clearInterval(id);
            return
        }
        $(e.target).text(time--+" 秒后重发");
    }, 1000);
}