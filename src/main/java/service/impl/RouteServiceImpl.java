package service.impl;

import dao.*;
import dao.impl.*;
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
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public Page<Route> getRoutesByCid(String cid, int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
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
        int totalPage = totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
        routePage.setTotalPage(totalPage);
        return routePage;
    }

    @Override
    public RouteDetails getRouteInfoById(String rid, String uid) {
        RouteDetails routeDetails = new RouteDetails();
        Route route = routeDao.getRouteById(rid);
        if (route == null) {
            return null;
        }
        Category category = categoryDao.getCategoryByCid(String.valueOf(route.getCid()));
        Seller seller = sellerDao.getSeller(String.valueOf(route.getSid()));
        List<RouteImg> routeImgs = routeImgDao.getRouteImgs(String.valueOf(route.getRid()));
        boolean isCollected = false;
        if (uid != null && !uid.isEmpty()) {
            isCollected = favoriteDao.isCollectRouteByUser(rid, uid);
        }

        routeDetails.setCategory(category);
        routeDetails.setRoute(route);
        routeDetails.setRouteImgList(routeImgs);
        routeDetails.setSeller(seller);
        routeDetails.setCollected(isCollected);

        return routeDetails;
    }

    @Override
    public Route collectRoute(String rid, String uid) {
        boolean isAdded = favoriteDao.addFavoriteRoute(rid, uid);
        Route route = null;
        if (isAdded) {
            int count = routeDao.getRouteCollectCount(rid);
            route = routeDao.updateRouteCollectCount(rid, count+1);
        }
        return route;
    }

    @Override
    public Route unCollectRoute(String rid, String uid) {
        boolean isDeleted = favoriteDao.deleteFavoriteRoute(rid, uid);
        Route route = null;
        if (isDeleted) {
            int count = routeDao.getRouteCollectCount(rid);
            route = routeDao.updateRouteCollectCount(rid, count -1 );
        }
        return route;
    }
}
