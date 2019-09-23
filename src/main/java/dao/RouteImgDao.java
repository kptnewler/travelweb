package dao;

import model.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> getRouteImgs(String rid);
}
