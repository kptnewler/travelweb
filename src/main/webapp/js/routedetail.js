layui.use(['layer', 'element'], function () {
    let layer = layui.layer,
    element = layui.element;

    $(function () {
       $.getJSON("/route/detail", {rid:getQueryString("rid")}, function (result) {
           if (result.success) {
                showRouteDetail(result.data);
           }
       });
        getCategories(element, 2);
    });

    $("header").load("../header.html");
});
function showRouteDetail(routeDetails) {
    let breadcrumb_category = $("#record-breadcrumb:nth-child(2)");
    breadcrumb_category.text(routeDetails.category.cname);
    breadcrumb_category.attr("href", "/routeDetails/all?cid="+routeDetails.category.cid);

    $(".info-group>h2").text(routeDetails.route.routeIntroduce);
    $(".info-group>p:first").text(routeDetails.route.rname);
    $(".seller-company").text("旅行社："+routeDetails.seller.sname);
    $(".seller-phone").text("咨询电话："+routeDetails.seller.consphone);
    $(".seller-address").text("地址："+routeDetails.seller.address);
    $(".price-info>span:first").text("￥"+routeDetails.route.price);
    $(".collect-count").text("已被"+routeDetails.route.count+"人收藏");
    let collect_btn = $(".collect-group>button");
    if (routeDetails.collected) {
        collect_btn.removeClass("collect-btn");
        collect_btn.addClass("cancel-collect-btn");
        collect_btn.text("取消收藏");
        collect_btn.bind("click", cancelCollect);
        collect_btn.unbind("click", collect);
    } else  {
        collect_btn.addClass("collect-btn");
        collect_btn.removeClass("cancel-collect-btn");
        collect_btn.text("点击收藏");
        collect_btn.bind("click", collect);
        collect_btn.unbind("click", cancelCollect);
    }

    let li_img = "";
    for (let i = 0; i < routeDetails.routeImgList.length; i++) {
        let route_img = routeDetails.routeImgList[i];
        if (i === 0) {
            li_img += "<li><img src=\""+route_img.smallPic+"\" class=\"small-cover-selected\" big-img= "+route_img.bigPic+"></li>\n";
            $(".big-cover>img").attr("src", route_img.bigPic);
        } else {
            li_img += "<li><img src='"+route_img.smallPic+ "' big-img='"+route_img.bigPic+"'></li>\n"
        }
    }
    $(".small-cover>ul").html(li_img);

    let lastSelect = $(".small-cover img:first");
    $(".small-cover img").mouseover(function (e) {
        $(e.target).addClass("small-cover-selected");
        lastSelect.removeClass("small-cover-selected");
        $(".big-cover img").attr("src", $(e.target).attr("big-img"));
        lastSelect = $(e.target);
    });

}

function collect() {
    $.post("/route/collect", {rid: getQueryString("rid")}, function (result) {
        let collect_btn = $(".collect-group>button");
        if (result.success) {
            collect_btn.removeClass("collect-btn");
            collect_btn.addClass("cancel-collect-btn");
            collect_btn.text("取消收藏");
            $(".collect-count").text("已被"+result.data.count+"人收藏");
            collect_btn.unbind("click", collect);
            collect_btn.bind("click", cancelCollect);
        } else {
            alert(result.msg);
        }
    },"json");
}
function cancelCollect() {
    $.post("/route/cancel-collect", {rid: getQueryString("rid")}, function (result) {
        let collect_btn = $(".collect-group>button");
        if (result.success) {
            collect_btn.addClass("collect-btn");
            collect_btn.removeClass("cancel-collect-btn");
            collect_btn.text("点击收藏");
            collect_btn.unbind("click", cancelCollect);
            collect_btn.bind("click", collect);
            $(".collect-count").text("已被"+result.data.count+"人收藏");
        } else {
            alert(result.msg);
        }
    },"json");
}