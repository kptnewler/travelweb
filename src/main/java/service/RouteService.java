package service;

import dto.Page;
import dto.RouteDetails;
import model.Route;

import java.sql.SQLException;
import java.util.List;

public interface RouteService {
    Page<Route> getRoutesByCid(String cid, int currentPage, int pageSize);
    RouteDetails getRouteInfoById(String rid, String uid);
    Route collectRoute(String rid, String uid);
    Route unCollectRoute(String rid, String uid);
}
