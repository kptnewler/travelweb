package dao;

import model.Route;

import java.util.List;

public interface RouteDao {
    List<Route> getPageRoutesByCid(String cid, int startIndex, int pageCount);
    int getRoutesCountByCid(String cid);
    Route getRouteInfoById(String sid);
}
