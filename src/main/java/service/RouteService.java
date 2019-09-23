package service;

import dto.Page;
import dto.RouteDetails;
import model.Route;

import java.util.List;

public interface RouteService {
    Page<Route> getRoutesByCid(String cid, int currentPage, int pageSize);
    RouteDetails getRouteInfoById(String rid);
}
