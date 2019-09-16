layui.use(['layer', 'form'], function(){
    const layer = layui.layer
        , form = layui.form;

    form.on('summit(*)', function (data) {
        alert(data);
        $.post("/user/login", $("form").serialize(), function (data) {
            if (data.success === false) {
                alert(data.msg)
            } else {
                alert("登录成功")
            }
        }, "json");
        return true;
    });
});