package service.impl;

import dao.RouteDao;
import dao.impl.RouteDaoImpl;
import dto.Page;
import model.Route;
import service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();
    @Override
    public Page<Route> getRoutesByCid(String cid, int currentPage, int pageSize) {
        int startIndex = (currentPage-1) * pageSize;
        Page<Route> routePage = new Page<>();
        int totalCount = routeDao.getRoutesCountByCID(cid);
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
}
