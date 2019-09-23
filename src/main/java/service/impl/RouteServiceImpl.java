package service.impl;

import dao.CategoryDao;
import dao.RouteDao;
import dao.RouteImgDao;
import dao.SellerDao;
import dao.impl.CategoryDaoImpl;
import dao.impl.RouteDaoImpl;
import dao.impl.RouteImgDaoImpl;
import dao.impl.SellerDaoImpl;
import dto.Page;
import dto.RouteDetails;
import model.Category;
import model.Route;
import model.RouteImg;
import model.Seller;
import service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public Page<Route> getRoutesByCid(String cid, int currentPage, int pageSize) {
        int startIndex = (currentPage-1) * pageSize;
        Page<Route> routePage = new Page<>();
        int totalCount = routeDao.getRoutesCountByCid(cid);
        routePage.setCurrentPage(currentPage);
        routePage.setPageSize(pageSize);
        routePage.setTotalCount(totalCount);
        if (startIndex >= totalCount) {
            routePage.setDataList(null);
            routePage.setTotalPage(startIndex / pageSize);
            return routePage;
        }
        List<Route> routes = routeDao.getPageRoutesByCid(cid, currentPage, pageSize);
        for (Route route : routes) {
            System.out.println(route.toString());
        }
        routePage.setDataList(routes);
        int totalPage = totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize)+1;
        routePage.setTotalPage(totalPage);
        return routePage;
    }

    @Override
    public RouteDetails getRouteInfoById(String rid) {
       RouteDetails routeDetails = new RouteDetails();
       Route route = routeDao.getRouteById(rid);
       if (route == null) {
           return null;
       }
        Category category = categoryDao.getCategoryByCid(String.valueOf(route.getCid()));
        Seller seller = sellerDao.getSeller(String.valueOf(route.getSid()));
        List<RouteImg> routeImgs = routeImgDao.getRouteImgs(String.valueOf(route.getRid()));

        routeDetails.setCategory(category);
        routeDetails.setRoute(route);
        routeDetails.setRouteImgList(routeImgs);
        routeDetails.setSeller(seller);

        return routeDetails;
    }
}
