package service;

import dto.Page;
import model.Route;

import java.util.List;

public interface RouteService {
    Page<Route> getRoutesByCid(String cid, int currentPage, int pageSize);
}
