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

function showRouteDetail(routeDetails) {
    $(".layui-breadcrumb:nth-child(2)").text(routeDetails.category.cname);
    $(".layui-breadcrumb:nth-child(2)").attr("href", "/routeDetails/all?cid="+routeDetails.category.cid);

    $(".info-group>h2").text(routeDetails.route.routeIntroduce);
    $(".info-group>p:first").text(routeDetails.route.rname);
    $(".seller-company").text("旅行社："+routeDetails.seller.sname);
    $(".seller-phone").text("咨询电话："+routeDetails.seller.consphone);
    $(".seller-address").text("地址："+routeDetails.seller.address);
    $(".price-info>span:first").text("￥"+routeDetails.route.price);

    let li_img = "";
    for (let i = 0; i < routeDetails.routeImgList.length; i++) {
        let route_img = routeDetails.routeImgList[i];
        if (i === 0) {
            li_img += "<li><img src=\""+route_img.smallPic+"\" class=\"small-cover-selected\" big-img=" + "route_img.bigPic></li>"
            $(".big-cover>img").attr("src", route_img.bigPic);
        } else {
            li_img += "<li><img src=\""+route_img.smallPic+"big-img="+route_img.bigPic+"></li>"
        }
    }
    $(".small-cover>ul").html(li_img);
}