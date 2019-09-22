/**
 * @return {string}
 */
function getQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
    return null;
}

layui.use(['layer', 'laypage'], function () {
    let layer = layui.layer,
        laypage = layui.laypage;
    var count = 1;

    $(function () {
        $.getJSON("/route/list", {cid: getQueryString("cid")}, function (result) {
            if (result.success) {
                laypage.render({
                    elem: 'route-page',
                    count: result.data.totalCount,
                    limit: result.data.pageSize,
                    jump: function (obj, first) {
                        if (!first) {
                            $.getJSON("/route/list",
                                {cid: getQueryString("cid"), pageSize: obj.limit,
                                currentPage:obj.curr},
                                function (result) {
                                    showRouteItems(result.data.dataList);
                            });
                        }
                    }
                });
                showRouteItems(result.data.dataList);
            } else {
                alert(result.msg)
            }
        })
    })
});

function showRouteItems(routeItems) {
    console.log(routeItems.length);
    let route_list = "";
    for (let i = 0; i < routeItems.length; i++) {
        let route_item_html =
        "<div class=\"route-item\">\n" +
        "   <div class=\"cover\"><img src=\"" + routeItems[i].rimage + "\"></div>" +
        "      <div class=\"detail\">\n" +
        "        <h3>" + routeItems[i].rname + "</h3>\n" +
        "           <p>" + routeItems[i].routeIntroduce + "</p>\n" +
        "             <div class=\"price\"><span>" + routeItems[i].price + "</span><span>起</span>\n" +
        "                 <button type=\"button\" class=\" layui-btn layui-btn-warm\">立即预定</button></div>" +
        "       </div>\n" +
        " </div>";
        route_list += route_item_html;
    }
    $(".route-list").html(route_list);
    window.scrollTo(0)
}

