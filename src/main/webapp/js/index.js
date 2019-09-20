layui.use(['element','carousel'], function(){
    let element = layui.element,
        carousel = layui.carousel;
    carousel.render({
        width:'100%',
        height:'400px',
        elem: '#carousel'
        ,arrow: 'always'
    });
    $(function () {
        getCategories(element)
    });
});


$("header").load("header.html");