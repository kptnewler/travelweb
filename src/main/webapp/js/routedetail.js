layui.use(['layer'], function () {
    let layer = layui.layer;

    let lastSelect = $(".small-cover img:first");
    $(".small-cover img").mouseover(function (e) {
        $(e.target).addClass("small-cover-selected");
        lastSelect.removeClass("small-cover-selected");
        $(".big-cover img").attr("src", $(e.target).attr("big-img"));
        lastSelect = $(e.target);
    });

    $(function () {
       $.getJSON("/route/detail", {rid:getQueryString("rid")}, function (result) {
           if (result.success) {
                showRouteDetail(result.data);
           } else {

           }
       })
    })
});

function showRouteDetail(route) {
    $(".layui-breadcrumb:nth-child(2)").text(route.category.cname);
    $(".layui-breadcrumb:nth-child(2)").attr("href", "/route/all?cid="+route.category.cid);

    $(".info-group>h2").text(route.routeIntroduce);
    $(".info-group>p:first").text(route.rname);
    $(".seller-company").text("旅行社："+route.seller.sname);
    $(".seller-phone").text("咨询电话："+route.seller.consphone);
    $(".seller-address").text("地址："+route.seller.address);
    $(".price-info>span").text("￥"+route.price);

    let li_img = "";
    for (let i = 0; i < route.routeImgList.length; i++) {
        let route_img = route.routeImgList[i];
        if (i === 0) {
            li_img += "<li><img src=\""+route_img.smallPic+"\" class=\"small-cover-selected\" big-img=" + "route_img.bigPic></li>"
            $(".big-cover>img").attr("src", route_img.bigPic);
        } else {
            li_img += "<li><img src=\""+route_img.smallPic+"big-img="+route_img.bigPic+"></li>"
        }
    }
    $(".small-cover>ul").html(li_img);
}