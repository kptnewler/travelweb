layui.use(['layer', 'form'], function(){
    const layer = layui.layer
        , form = layui.form;

    form.on('submit(*)', function(data){
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    $(".layui-btn").click(function () {
        $.post("/user/login", $("form").serialize(), function (data) {
            console.info(JSON.stringify(data));

            if (data.success === false) {
                alert(data.msg);
            } else {
                alert("登录成功");
                location.href = "/";
            }
        }, "json");
    })
});