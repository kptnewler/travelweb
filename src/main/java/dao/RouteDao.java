package dao;

import model.Route;
import model.User;

import java.util.List;

public interface RouteDao {
    List<Route> getPageRoutesByCid(String cid, int startIndex, int pageCount);
    int getRoutesCountByCid(String cid);
    Route getRouteById(String rid);
    Route updateRouteCollectCount(String rid);
}
