package dao;

import model.Route;

import java.util.List;

public interface RouteDao {
    List<Route> getPageRoutesByCid(String cid, int startIndex, int pageCount);
    int getRoutesCountByCID(String cid);
}
