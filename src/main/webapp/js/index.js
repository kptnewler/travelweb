layui.use(['element','carousel'], function(){
    var element = layui.element,
        carousel = layui.carousel;
    carousel.render({
        width:'100%',
        height:'400px',
        elem: '#carousel'
        ,arrow: 'always'
    });
});

$("header").load("header.html");