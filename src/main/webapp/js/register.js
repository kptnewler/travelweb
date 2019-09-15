layui.use(['layer', 'form', 'laydate'], function(){
    const layer = layui.layer
        , form = layui.form,
        date = layui.laydate;

    date.render({
        elem: '#test1'
    });
});
